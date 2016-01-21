# convex-hull

Algorithms to find the convex hull of a set of points in a plane. Also generates points useful for testing itself.

## Installation

Clone the repo. Use [Gradle](http://gradle.org/) to build the standalone JAR.

    $ gradle shadowJar

## Usage

Run the standalone JAR file.

    $ java -jar build/libs/convex-hull-1.0-all.jar <args>

See the next section for the arguments.

## Options

To find the convex hull of points in a file, specify two arguments:

    $ java -jar build/libs/convex-hull-1.0-all.jar <path to file> <algorithm>

The `<algorithm>` must be one of the following values:

- gs: [Graham Scan](https://en.wikipedia.org/wiki/Graham_scan)
- jm: [Jarvis March](https://en.wikipedia.org/wiki/Gift_wrapping_algorithm)

The file should contain pairs of x-y coordinates separated by spaces. There should be one pair per line. Lines starting with `#` will be ignored.

The convex hull will be output to standard output.

The program can also generate a set of points. To do this, specify two arguments

    $ java -jar build/libs/convex-hull-1.0-all.jar <number of points> <max x or y coord>

## Examples

Finding the convex hull using Graham Scan for points in the file at `/home/user/points.dat`:

    $ java -jar build/libs/convex-hull-1.0-all.jar /home/user/points.dat gs 

You can redirect the output to a file:

    $ java -jar build/libs/convex-hull-1.0-all.jar /home/user/points.dat gs > /home/user/output.dat

Generating 1000 in the plane bounded by (0,0), (0,50), (50,0), and (50,50):

    $ java -jar build/libs/convex-hull-1.0-all.jar 1000 50

Of course, you can redirect the points to a file as well:

    $ java -jar build/libs/convex-hull-1.0-all.jar 1000 50 > /home/user/test.dat

## Known bugs

The Jarvis March implementation does not handle collinear points properly.

## Notes

You can use [gnuplot](http://www.gnuplot.info/) to plot the output of this program

Plotting points:

    gnuplot> plot '~/convex-hull/points.dat' with points pointtype 7

Plotting lines:

    gnuplot> plot '~/convex-hull.dat' with linespoints ls 1

## License

Copyright © 2016 Corey Beres

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
