package net.seabears;

import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** Utilities for {@link Point} */
public final class PointUtils {
  private PointUtils() {
    throw new UnsupportedOperationException("cannot instantiate " + getClass());
  }

  /** Reads points read from the file, one point per line. */
  public static List<Point> readFromFile(String path) throws IOException {
    return Files.lines(Paths.get(path))
        .filter(s -> !s.startsWith("#"))
        .map(Point::fromString)
        .collect(toCollection(ArrayList::new));
  }

  /** Returns a single-use stream of points in a Gaussian distribution */
  public static Stream<Point> random(int n, int m) throws IOException {
    // a gussian distribution of points seems to make more sense than a normal
    // distribution for finding the convex hull
    final Random r = new Random();
    return IntStream.range(0, n).mapToObj(i -> new Point(r.nextGaussian() * m, r.nextGaussian() * m));
  }

  /**
   * Outputs elements to a file at the specified path, one element per line.<p>
   * A gnuplot header is output first.
   */
  public static void outputToFile(String path, Iterable<?> points) throws IOException {
    try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(path)))) {
      writer.println("# x y");
      points.forEach(writer::println);
    }
  }

  /**
   * Outputs elements to standard output, one element per line.<p>
   * A gnuplot header is output first.
   */
  public static void output(Iterable<?> points) throws IOException {
    System.out.println("# x y");
    points.forEach(System.out::println);
  }
}
