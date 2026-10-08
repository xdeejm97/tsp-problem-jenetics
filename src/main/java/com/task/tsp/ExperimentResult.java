package com.task.tsp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ExperimentResult {

  private final double bestDistance;
  private final List<Integer> bestRoute;

}
