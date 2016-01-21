package net.seabears;

import java.util.Collections;
import java.util.List;

/** <a href="https://en.wikipedia.org/wiki/Graham_scan">Graham Scan</a> */
public class GrahamScan extends ConvexHullAlg {
  /** Creates an instance of the algorithm for the specified points */
  public GrahamScan(List<Point> points) {
    super(points);
  }

  @Override
  protected List<Point> doExecute(Point min) {
    // we want points[0] to be a sentinel point that will stop the loop
    points.add(0, points.get(n - 1));

    // m will denote the number of points on the convex hull
    int m = 1;
    for (int i = m + 1; i <= n; ++i) {
      // find next valid point on convex hull
      while (points.get(m - 1).ccw(points.get(m), points.get(i)) <= 0) {
        if (m > 1) {
          --m;
        } else if (i == n) {
          // all points are collinear
          break;
        } else {
          ++i;
        }
      }

      // update m and swap points[i] to the correct place
      Collections.swap(points, ++m, i);
    }
    return points.subList(0, m);
  }
}
