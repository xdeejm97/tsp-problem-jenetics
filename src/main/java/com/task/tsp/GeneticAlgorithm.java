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

public class GeneticAlgorithm {
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

    private static Phenotype<EnumGene<Integer>, Double> getBestResult(Engine<EnumGene<Integer>, Double> engine, EvolutionStatistics<Double, ?> stats) {
        return engine.stream()
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
    }

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
