package com.task.tsp;

import io.jenetics.EnumGene;
import io.jenetics.Optimize;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.Phenotype;
import io.jenetics.SwapMutator;
import io.jenetics.TournamentSelector;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;

/**
 * Problem komiwojażera (TSP) dla 16 miast wojewódzkich Polski
 * rozwiązywany algorytmem genetycznym z biblioteki Jenetics.
 *
 * Genotyp: permutacja indeksów miast (chromosom permutacyjny).
 * Fitness: długość zamkniętej trasy w km (minimalizowana).
 */
public class Application {

  private static final String[] NAMES = {
          "Kraków", "Warszawa", "Gdańsk", "Wrocław", "Poznań", "Szczecin",
          "Łódź", "Lublin", "Białystok", "Katowice", "Rzeszów", "Olsztyn",
          "Bydgoszcz", "Kielce", "Opole", "Zielona Góra"
  };

  // {szerokość, długość geograficzna}
  private static final double[][] COORDS = {
          {50.06, 19.94}, {52.23, 21.01}, {54.35, 18.65}, {51.11, 17.04},
          {52.41, 16.93}, {53.43, 14.55}, {51.76, 19.46}, {51.25, 22.57},
          {53.13, 23.17}, {50.26, 19.02}, {50.04, 22.00}, {53.78, 20.49},
          {53.12, 18.01}, {50.87, 20.63}, {50.67, 17.93}, {51.94, 15.51}
  };

  private static final int N = NAMES.length;
  private static final double[][] DIST = new double[N][N];

  static {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        DIST[i][j] = haversine(COORDS[i], COORDS[j]);
      }
    }
  }

  /** Odległość po sferze Ziemi (km). */
  private static double haversine(double[] a, double[] b) {
    final double r = 6371.0;
    double dLat = Math.toRadians(b[0] - a[0]);
    double dLon = Math.toRadians(b[1] - a[1]);
    double h = Math.pow(Math.sin(dLat / 2), 2)
            + Math.cos(Math.toRadians(a[0])) * Math.cos(Math.toRadians(b[0]))
            * Math.pow(Math.sin(dLon / 2), 2);
    return 2 * r * Math.asin(Math.sqrt(h));
  }

  /** Funkcja przystosowania: długość cyklu Hamiltona. */
  private static double tourLength(final int[] tour) {
    double sum = 0;
    for (int i = 0; i < tour.length; i++) {
      sum += DIST[tour[i]][tour[(i + 1) % tour.length]];
    }
    return sum;
  }

  public static void main(String[] args) {
    final var codec = Codecs.ofPermutation(N);

    final Engine<EnumGene<Integer>, Double> engine = Engine
            .builder(Application::tourLength, codec)
            .optimize(Optimize.MINIMUM)
            .populationSize(200)
            .survivorsSelector(new TournamentSelector<>(3))
            .offspringSelector(new TournamentSelector<>(3))
            .alterers(
                    new PartiallyMatchedCrossover<>(0.7),
                    new SwapMutator<>(0.2))
            .build();

    final EvolutionStatistics<Double, ?> stats = EvolutionStatistics.ofNumber();

    final Phenotype<EnumGene<Integer>, Double> best = engine.stream()
            .limit(Limits.bySteadyFitness(150)) // stop po 150 pokoleniach bez poprawy
            .limit(2000)                        // twardy limit pokoleń
            .peek(stats)
            .peek(r -> {
              if (r.generation() % 25 == 0) {
                System.out.printf("Pokolenie %4d | najlepsza trasa: %.1f km%n",
                        r.generation(), r.bestFitness());
              }
            })
            .collect(EvolutionResult.toBestPhenotype());

    System.out.println();
    System.out.println(stats);

    final int[] tour = codec.decode(best.genotype());

    System.out.printf("%nNajlepsza znaleziona trasa: %.1f km%n", best.fitness());
    final StringBuilder sb = new StringBuilder();
    for (int idx : tour) {
      sb.append(NAMES[idx]).append(" -> ");
    }
    sb.append(NAMES[tour[0]]);
    System.out.println(sb);
  }
}