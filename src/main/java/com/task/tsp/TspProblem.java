package com.task.tsp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class TspProblem {

  private final List<City> cities;

  public double calculateDistance(List<Integer> route) {
    double distance = 0.0;

    for (int i = 0; i < route.size() - 1; i++) {
      City current = cities.get(route.get(i));
      City next = cities.get(route.get(i + 1));

      distance += current.distanceTo(next);
    }

    // Powrót do miasta początkowego
    City last = cities.get(route.get(route.size() - 1));
    City first = cities.get(route.get(0));

    distance += last.distanceTo(first);

    return distance;
  }

}
