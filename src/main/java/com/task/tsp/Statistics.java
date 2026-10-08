package com.task.tsp;

import java.util.List;

public class Statistics {

  public static double mean(List<Double> values) {

    return values.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
  }

  public static double standardDeviation(List<Double> values) {

    double mean = mean(values);

    double variance = values.stream()
            .mapToDouble(value ->
                    Math.pow(value - mean, 2))
            .average()
            .orElse(0.0);

    return Math.sqrt(variance);
  }

}
