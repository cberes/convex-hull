package net.seabears;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

/** Convex-hull finder */
public class App {
  /** Returns map of algorithms by short codes. */
  private static Map<String, Class<? extends ConvexHullAlg>> convexHullAlgs() {
    final Map<String, Class<? extends ConvexHullAlg>> algs = new HashMap<>();
    algs.put("gs", GrahamScan.class);
    algs.put("jm", JarvisMarch.class);
    return algs;
  }

  private static void e(String s) {
    System.err.println(s);
  }

  private static void help() {
    e("I didn't understand that.");
    e("");
    e("To find the convex hull of points in a file, invoke with two arguments:");
    e("\t<path to file> <algorithm>");
    e("where <algorithm> is one of the following:");
    e("\tgs\tGraham Scan");
    e("\tjm\tJarvis March");
    e("");
    e("To generate points for testing, invoke with two arguments:");
    e("\t<number of points> <max x or y coord>");
  }

  /** Returns the convex hull of points in the specified file using the specified algorithm */
  private static List<Point> findConvexHull(String path, Class<? extends ConvexHullAlg> alg) throws IOException {
    // read points from file
    List<Point> input = PointUtils.readFromFile(path);
    // find convex hull
    return ConvexHullAlg.create(input, alg).execute();
  }

  /** Main entry point */
  public static void main(String[] args) throws IOException {
    try {
      // generate specified number of points at random
      // this means that no algorithms should have a numeric code in convexHullAlgs()
      Stream<Point> points = PointUtils.random(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      PointUtils.output((Iterable<Point>) points::iterator);
    } catch (NumberFormatException e) {
      // assume convex hull is to be found
      // look up the algorithm by the code
      final String code = args[1].toLowerCase(Locale.ENGLISH);
      Class<? extends ConvexHullAlg> alg = convexHullAlgs().get(code);
      if (alg != null) {
        PointUtils.output(findConvexHull(args[0], alg));
      } else {
        // not sure what user wants
        help();
      }
    }
  }
}
