package net.seabears;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/** Base class for convex-hull algorithms */
public abstract class ConvexHullAlg {
  /** Returns an instance of the specified convex-hull algorithm for the specified */
  public static ConvexHullAlg create(List<Point> input, Class<? extends ConvexHullAlg> alg) {
    try {
      return alg.getConstructor(List.class).newInstance(input);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      // just re-throw as a runtime exception
      throw new IllegalStateException(e);
    }
  }

  protected static int polarAngle(Point min, Point a, Point b) {
    double res = min.ccw(a, b);
    return res < 0.0 ? 1 : res > 0.0 ? -1 : Double.compare(min.distanceSquared(a), min.distanceSquared(b));
  }

  /** data set */
  protected final List<Point> points;

  /** number of points */
  protected final int n;

  /** Creates a new algorithm */
  public ConvexHullAlg(List<Point> points) {
    this.points = new ArrayList<>(points);
    this.n = this.points.size();
  }

  protected Point removeLowestPoint() {
    final int min = IntStream.range(0, points.size()).boxed()
        .sorted((i, j) -> points.get(i).compareTo(points.get(j)))
        .findFirst().get();
    return points.remove(min);
  }

  /** Returns the convex hull. */
  public List<Point> execute() {
    // if no points, return no points
    if (points.isEmpty()) {
      return Collections.emptyList();
    }

    // get the point with the lowest y coordinate
    final Point min = removeLowestPoint();

    // sort points by polar angle with min point
    // apparently this does a "TimSort," which is pretty efficient
    points.sort((a, b) -> polarAngle(min, a, b));

    // add min point to the list
    points.add(0, min);

    // perform algorithm-specific steps
    return doExecute(min);
  }

  /** Performs algorithm-specific steps */
  protected abstract List<Point> doExecute(Point min);
}
