package net.seabears;

import java.util.LinkedList;
import java.util.List;

/** <a href="https://en.wikipedia.org/wiki/Gift_wrapping_algorithm">Jarvis March</a> */
public class JarvisMarch extends ConvexHullAlg {
  public JarvisMarch(List<Point> points) {
    super(points);
  }

  @Override
  protected List<Point> doExecute(Point min) {
    final List<Point> hull = new LinkedList<>();
    Point pointOnHull = min;
    Point endpoint;
    do {
      // add point to the hull
      hull.add(pointOnHull);

      // pick a candidate for the hull
      endpoint = points.get(0);

      // find the next point on the null
      for (int j = 1; j < points.size(); ++j) {
        // pick a new candidate if either...
        //  the previous candidate was just found to be on the hull, or
        //  the point is makes a counter-clockwise point with the last point
        //  found to be on the hull and the previous candidate
        if (endpoint.equals(pointOnHull) || pointOnHull.ccw(endpoint, points.get(j)) > 0) {
          endpoint = points.get(j);
        }
      }

      // we found the next point on the hull
      // exit the loop if this point is already known to be in the hull
      pointOnHull = endpoint;
    } while (!pointOnHull.equals(hull.get(0)));
    return hull;
  }
}
