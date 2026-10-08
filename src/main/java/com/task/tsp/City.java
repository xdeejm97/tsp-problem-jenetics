package com.task.tsp;

import lombok.Data;

@Data
public class City {

  public static final String[] CITY_NAMES = {
          "Kraków", "Warszawa", "Gdańsk", "Wrocław", "Poznań", "Szczecin",
          "Łódź", "Lublin", "Białystok", "Katowice", "Rzeszów", "Olsztyn",
          "Bydgoszcz", "Kielce", "Opole", "Zielona Góra"
  };

  // szerokość, długość geograficzna
  private static final double[][] COORDS = {
          {50.06, 19.94}, {52.23, 21.01}, {54.35, 18.65}, {51.11, 17.04},
          {52.41, 16.93}, {53.43, 14.55}, {51.76, 19.46}, {51.25, 22.57},
          {53.13, 23.17}, {50.26, 19.02}, {50.04, 22.00}, {53.78, 20.49},
          {53.12, 18.01}, {50.87, 20.63}, {50.67, 17.93}, {51.94, 15.51}
  };

  public static final int N = CITY_NAMES.length;
  protected static final double[][] DIST = new double[N][N];

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
  public static double tourLength(final int[] tour) {
    double sum = 0;
    for (int i = 0; i < tour.length; i++) {
      sum += DIST[tour[i]][tour[(i + 1) % tour.length]];
    }
    return sum;
  }

}
