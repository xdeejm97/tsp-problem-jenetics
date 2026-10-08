//package com.task.travelingsalesman;
//
//import io.jenetics.EnumGene;
//import io.jenetics.Genotype;
//import io.jenetics.PartiallyMatchedCrossover;
//import io.jenetics.PermutationChromosome;
//import io.jenetics.Phenotype;
//import io.jenetics.SwapMutator;
//import io.jenetics.TournamentSelector;
//import io.jenetics.engine.Engine;
//import io.jenetics.engine.EvolutionResult;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public class GeneticAlgorithm {
//
//    private final TspProblem problem;
//    private final int populationSize;
//    private final int generations;
//
//    public Phenotype<Integer, Double> run() {
//
//        int numberOfCities = problem.getCities().size();
//
//        Genotype<EnumGene<Integer>> factory = Genotype.of(PermutationChromosome.ofInteger(numberOfCities));
//
//        Engine<Integer, Double> engine = Engine.builder(
//                this::fitness,
//                factory)
//                .populationSize(populationSize)
//                .alterers(
//                    new PartiallyMatchedCrossover<Integer>(0.8),
//                    new SwapMutator<Integer>(0.2))
//                .selector(new TournamentSelector<>(3))
//                .build();
//
//        EvolutionResult<Integer, Double> result = engine.stream()
//            .limit(generations)
//            .collect(EvolutionResult.toBestEvolutionResult());
//
//        return result.best();
//    }
//
//    private Double fitness(Genotype<Integer> genotype) {
//
//        var chromosome = genotype.chromosome();
//
//        List<Integer> route = chromosome.geneStream()
//                .map(Integer::intValue)
//                .toList();
//
//        return problem.calculateDistance(route);
//    }
//
//
//}
