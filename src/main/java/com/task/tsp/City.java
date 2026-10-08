package com.task.tsp;

import lombok.Data;

@Data
public class City {

  private final int id;
  private final double x;
  private final double y;

  public double distanceTo(City other) {
    double dx = x - other.x;
    double dy = y - other.y;

    return Math.sqrt(dx * dx + dy * dy);
  }

}
