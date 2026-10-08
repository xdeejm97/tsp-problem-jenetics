package com.task.tsp;

/**
 * Problem komiwojażera (TSP) dla 16 miast wojewódzkich Polski
 * rozwiązywany algorytmem genetycznym z biblioteki Jenetics.
 *
 * Genotyp: permutacja indeksów miast (chromosom permutacyjny).
 * Fitness: długość zamkniętej trasy w km (minimalizowana).
 */
public class Application {

  static void main() {
    GeneticAlgorithm ga = new GeneticAlgorithm();
    ga.compute();
  }
}