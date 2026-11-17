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
import io.jenetics.engine.InvertibleCodec;
import io.jenetics.engine.Limits;

/**
 * Osobniki reprezentują permutacje indeksów miast, a ich funkcją
 * przystosowania jest całkowita długość zamkniętej trasy. Klasa korzysta
 * z biblioteki Jenetics do inicjalizacji populacji, selekcji, krzyżowania,
 * mutacji i określenia kryterium zakończenia ewolucji.
 */
public class GeneticAlgorithm {
    /**
     * Uruchamia ewolucję i wyświetla najlepszą znalezioną trasę oraz statystyki.
     *
     * Wynik jest dekodowany z kodera permutacyjnego do tablicy indeksów
     * miast, po czym indeksy są zamieniane na nazwy miast.
     */
    public void compute() {
        final var codec = Codecs.ofPermutation(City.N);
        final Engine<EnumGene<Integer>, Double> engine = getGeneEngine(codec);
        final EvolutionStatistics<Double, ?> stats = EvolutionStatistics.ofNumber();

        final Phenotype<EnumGene<Integer>, Double> best = getBestResult(engine, stats);

        System.out.println();
        System.out.println(stats);

        final int[] tour = codec.decode(best.genotype());

        System.out.printf("%nNajlepsza znaleziona trasa: %.1f km%n", best.fitness());

        final StringBuilder sb = new StringBuilder();
        for (int idx : tour) {
            sb.append(City.CITY_NAMES[idx]).append(" -> ");
        }
        sb.append(City.CITY_NAMES[tour[0]]);

        System.out.println(sb);
    }

    /**
     * Przeszukuje kolejne pokolenia i zwraca osobnik o najlepszym fitness.
     *
     * @param engine skonfigurowany silnik ewolucyjny Jenetics
     * @param stats obiekt zbierający statystyki ewolucji i populacji
     * @return fenotyp osobnika o najmniejszej długości trasy
     */
    private static Phenotype<EnumGene<Integer>, Double> getBestResult(Engine<EnumGene<Integer>, Double> engine, EvolutionStatistics<Double, ?> stats) {
        return engine.stream()
                .limit(Limits.bySteadyFitness(150)) // stop po 150 pokoleniach bez poprawy
                .limit(2000)                                 // twardy limit pokoleń
                .peek(stats)
                .peek(r -> {
                    if (r.generation() % 25 == 0) {
                        System.out.printf("Pokolenie %4d | najlepsza trasa: %.1f km%n",
                                r.generation(), r.bestFitness());
                    }
                })
                .collect(EvolutionResult.toBestPhenotype());
    }

    /**
     * Tworzy silnik algorytmu genetycznego z parametrami potrzebnymi dla TSP.
     *
     * @param codec koder odwzorowujący osobnika na permutację indeksów miast
     * @return skonfigurowany silnik minimalizujący długość trasy
     */
    private static Engine<EnumGene<Integer>, Double> getGeneEngine(InvertibleCodec<int[], EnumGene<Integer>> codec) {
        return Engine
                .builder(City::tourLength, codec)
                .optimize(Optimize.MINIMUM)
                .populationSize(200)
                .survivorsSelector(new TournamentSelector<>(3))
                .offspringSelector(new TournamentSelector<>(3))
                .alterers(
                        new PartiallyMatchedCrossover<>(0.7),
                        new SwapMutator<>(0.2))
                .build();
    }

}
