# :christmas_tree: Advent of Code Library

![GitHub](https://img.shields.io/github/license/TomPlum/advent-of-code-2020?color=informational)
![GitHub](https://img.shields.io/badge/instructions-98%25-success)
![GitHub](https://img.shields.io/badge/branches-93%25-orange)

## About
A simple Kotlin library for Advent of Code.

After my second year of completing AoC in Kotlin, I extracted common functionality into a library to reduce redundancy.
Two packages are published;

#### `advent-of-code-libs`
The main utility classes and datastructures to be compiled in the `implementation` scope.

#### `advent-of-code-test-support`
Test utility classes for supporting unit tests and benchmarking for the `testImplementation` scope.

<!-- TOC -->
* [:christmas_tree: Advent of Code Library](#christmas_tree-advent-of-code-library)
  * [About](#about)
      * [`advent-of-code-libs`](#advent-of-code-libs)
      * [`advent-of-code-test-support`](#advent-of-code-test-support)
  * [Implementation Library](#implementation-library)
    * [Logging](#logging)
    * [Input De-Serialisation](#input-de-serialisation)
    * [Math](#math)
    * [Solution Running & Benchmarking](#solution-running--benchmarking)
    * [Documentation](#documentation)
      * [Extension Functions](#extension-functions)
        * [Collections Extension Functions](#collections-extension-functions)
        * [Primitive Extension Functions](#primitive-extension-functions)
        * [Range Extension Functions](#range-extension-functions)
        * [Tuple Extension Functions](#tuple-extension-functions)
      * [Graphing Algorithms](#graphing-algorithms)
  * [Test Support Library](#test-support-library)
    * [VisualVM Support](#visualvm-support)
  * [Release Instructions](#release-instructions)
  * [To-Do List](#to-do-list)
<!-- TOC -->

## Implementation Library
### Logging
The [`AdventLogger`](https://git.io/JILT9) provides a simple companion-object class that wraps the
[SLF4J](http://www.slf4j.org/) abstraction. The class exposes the usual levels which are as follows

| Level | Description                                                                                                |
|-------|------------------------------------------------------------------------------------------------------------|
| Error | Designates error events that might still allow the application to continue running.                        |
| Warn  | Designates potentially harmful situations.                                                                 |
| Info  | Designates information messages that highlight the progress of the application at a coarse-grained level.  |
| Debug | Designates fine-grained informational events that are most useful to debug the application.                |
| Trace | Designates finer-grained informational events than the debug level.                                        |

### Input De-Serialisation
The [`InputReader`](https://git.io/JILkc) provides an easy way to read the puzzle inputs from the `resources` directory
of the main source set. The reader looks for a file named `input.txt` in a sub-folder named `dayX` where `X` is the day 
number. The `read()` function also expects a generic type parameter which changes the type of collection that is returned.
The currently supported types are `Integer` and `String`.

### Math
A common theme in the Advent of Code is maps, which are usually represented on a 2D cartesian grid. Thus,
[`Point2D`](https://git.io/JImDn) is a wrapper class for co-ordinates that provides useful translation, transformation
and interacting with other points. Likewise, [`Point3D`](https://git.io/JImDz) does a similar thing for 3-Dimensions.

To accompany the `Point` classes, [`AdventMap2D`](https://git.io/JIsth) and [`AdventMap3D`](https://git.io/JIsqe) have 
been created for `Point2D` and `Point3D`respectively. These classes are essentially cartesian grids backed by a `Map` 
of the respective `Point` class against a [`MapTile`](https://git.io/JIsqt) which accepts a generic type.
This is usually a `Char` due to the nature of Advent of Code puzzle inputs.

An example puzzle input of a map that could be read and stored in an `AdventMap2D`;

    ..##.......
    #...#...#..
    .#....#..#.
    ..#.#...#.#
    .#...##..#.
    ..#.##.....
    .#.#.#....#
    .#........#
    #.##...#...
    #...##....#
    .#..#...#.#

### Solution Running & Benchmarking
A `Solution` interface is provided so that the solution classes can be passed to the `SolutionRunner`. 
The runner executes all the solution implementations and measures their runtimes.

A benchmark is created that produces a report that is appended to the SystemOut.
The benchmark utility stores the last run in `benchmark.xml` in the root of the project directory.

The format and verbosity of the report can be changed via a JVM arg called `report`.
It can have the values `verbose` or `compact`. E.g. `-Dreport=verbose`.

A snippet from a verbose runtime delta report;

    - Advent of Code 2020 Solution Report -
    
    [Day 1]
    Part 1: 802011
    Execution Time: 9ms (+3ms)
    
    Part 2: 248607374
    Execution Time: 54ms (+22ms)
    
    [Day 2]
    Part 1: 660
    Execution Time: 10ms (+4ms)
    
    Part 2: 530
    Execution Time: 6ms (+2ms)
    
    ...
    
    Average Execution Time: 654ms (+21ms)
    Total Execution Time: 16s 365ms (+535ms)

If a previous run cannot be found (I.e. the `benchmark.xml` is not present), then a delta report cannot be produced.
There will be an info log printed, and the report will have the deltas omitted like the following;

    Cannot find previous run. Deltas will not be reported.
    
    - Advent of Code 2020 Solution Report -
    
    [Day 1]
    Part 1: 802011
    Execution Time: 7ms
    
    Part 2: 248607374
    Execution Time: 41ms

    ...

If your terminal supports ANSI escape codes then the deltas will be green or red for increased and decreased runtimes
respectively.

### Documentation
A complete list of all publicly exposed classes and functions.

#### Extension Functions

##### Collections Extension Functions

<table>
  <thead>
    <tr>
      <th>Function Signature</th>
      <th>Behaviour</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <pre lang="kotlin"><code>List&lt;Int&gt;.product(): Int</code></pre>
      </td>
      <td>
        Returns the product of all the integers in the given list.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>List&lt;Long&gt;.product(): Long</code></pre>
      </td>
      <td>
        Returns the product of all the longs in the given list.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>IntArray.toDecimal(): Long</code></pre>
      </td>
      <td>
        Converts the <code>IntArray</code> into its decimal equivalent. Assumes the array contains only 1s and 0s.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin">
          <code>
&lt;S, T&gt; List&lt;S&gt;.cartesianProduct(
  other: List&lt;T&gt;
): List&lt;Pair&lt;S, T&gt;>
          </code>
        </pre>
      </td>
      <td>
        For two sets A and B, the Cartesian product of A and B is denoted by A×B and defined as 
        <code>A×B = { (a,b) | aϵA and bϵB }</code>. Put simply, the Cartesian Product is the multiplication 
        of two sets to form the set of all ordered pairs. Returns the cartesian product of itself 
        and the given set, meaning A and B are <code>this</code> and <code>other</code>.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>
&lt;T&gt; List&lt;T&gt;.cartesianProductQuadratic(
): List&lt;Pair&lt;T, T&gt;>
        </code></pre>
      </td>
      <td>
        For two sets A and B, the Cartesian product of A and B is denoted by A×B and defined as 
        <code>A×B = { (a,b) | aϵA and bϵB }</code>. Put simply, the Cartesian Product is the multiplication 
        of two sets to form the set of all ordered pairs. Returns the cartesian product of itself, 
        meaning both A and B are simply <code>this</code>.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>
&lt;T&gt; List&lt;T&gt;.cartesianProductCubic(
): List&lt;Triple&lt;T, T, T&gt;>
        </code></pre>
      </td>
      <td>
        For three sets A, B and C, the Cartesian product of A, B and C is denoted by A×B×C and 
        defined as <code>A×B×C = { (p, q, r) | pϵA and qϵB and rϵC }</code>. Put simply, the Cartesian Product 
        is the multiplication of three sets to form the set of all ordered pairs. Returns the 
        cartesian product of itself and the given sets, meaning that A, B & C are all <code>this</code>.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>
&lt;T&gt; List&lt;T&gt;.cartesianProductCubic(
  second: List&lt;T&gt;, 
  third: List&lt;T&gt;
): List&lt;Triple&lt;T, T, T&gt;>
        </code></pre>
      </td>
      <td>
        For three sets A, B and C, the Cartesian product of A, B and C is denoted by A×B×C and 
        defined as <code>A×B×C = { (p, q, r) | pϵA and qϵB and rϵC }</code>. Put simply, the Cartesian Product 
        is the multiplication of three sets to form the set of all ordered pairs. Returns the 
        cartesian product of itself and the given sets, meaning both A, B and C are <code>this</code>, 
        <code>second</code> and <code>third</code> respectively.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>
&lt;T&gt; cartesianProduct(
  vararg sets: List&lt;T&gt;
): List&lt;List&lt;T&gt;>
        </code></pre>
      </td>
      <td>
        Finds the Cartesian Product of any number of given sets.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>
&lt;T&gt; Collection&lt;T&gt;.distinctPairs()
returns List&lt;Pair&lt;T, T&gt;>
        </code></pre>
      </td>
      <td>
        Produces a list of all distinct pairs of elements from the given collection. Pairs are 
        considered distinct irrespective of their order.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>
&lt;T&gt; Collection&lt;T&gt;.split(
  predicate: (element: T) -> Boolean
): Collection&lt;Collection&lt;T&gt;&gt;
        </code></pre>
      </td>
      <td>
        Splits a collection based on a given predicate.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin">
          <code>
&lt;L, R&gt; Collection&lt;String&gt;.toVerticalLists(
  parse: (String) -> Pair&lt;L, R&gt;?
): Pair&lt;MutableList&lt;L&gt;, MutableList&lt;R&gt;>
          </code>
        </pre>
      </td>
      <td>
        Parses a collection of Strings (Usually puzzle input lines) vertically to produce two lists.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>List&lt;Long&gt;.lcm(): Long</code></pre>
      </td>
      <td>
        Calculates the lowest common multiple of all the long values of this given list.
      </td>
    </tr>
  </tbody>
</table>

##### Primitive Extension Functions

<table>
  <thead>
    <tr>
      <th>Function Signature</th>
      <th>Behaviour</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <pre lang="kotlin"><code>Int.toBinary(bits: Int): IntArray</code></pre>
      </td>
      <td>
         Converts an <code>Int</code> into its binary equivalent. Pads the number with trailing 0s to reach the number of given bits.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>Double.toRadians(): Double</code></pre>
      </td>
      <td>
         Converts an angle in degrees into radians.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>Int.nthBinomialCoefficient(): Int</code></pre>
      </td>
      <td>
        Calculate the value at the given position in Pascal's triangle. This can be expressed as <code>(n^2 + n) / 2</code> as a binomial coefficient.
      </td>
    </tr>
  </tbody>
</table>

##### Range Extension Functions

<table>
  <thead>
    <tr>
      <th>Function Signature</th>
      <th>Behaviour</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <pre lang="kotlin"><code>IntRange.midpoint(): Int</code></pre>
      </td>
      <td>
         Finds the midpoint in an <code>IntRange</code>.
      </td>
    </tr>
  </tbody>
</table>

##### Tuple Extension Functions

<table>
  <thead>
    <tr>
      <th>Function Signature</th>
      <th>Behaviour</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <pre lang="kotlin"><code>Pair<&lt;Int, Int&gt;.product(): Int</code></pre>
      </td>
      <td>
         Calculates the product of the integer values of a given <code>Pair</code>.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>Pair&lt;Int, Int&gt;.sum(): Int</code></pre>
      </td>
      <td>
         Calculates the sum of the integer values of a given <code>Pair</code>.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>Pair&lt;Long, Long&gt;.sum(): Long</code></pre>
      </td>
      <td>
         Calculates the sum of the long values of a given <code>Pair</code>.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>Triple&lt;Int, Int, Int&gt;.sum(): Int</code></pre>
      </td>
      <td>
         Calculates the sum of the integer values of a given <code>Triple</code>.
      </td>
    </tr>
    <tr>
      <td>
        <pre lang="kotlin"><code>Triple&lt;Int, Int, Int&gt;.product(): Int</code></pre>
      </td>
      <td>
         Calculates the product of the integer values of a given <code>Triple</code>.
      </td>
    </tr>
  </tbody>
</table>

#### Graphing Algorithms

<table>
  <thead>
    <tr>
      <th>Function Signature</th>
      <th>Behaviour</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <pre lang="kotlin">
            <code>
&lt;N&gt; dijkstraShortestPath(
    startingPositions: Collection&lt;N&gt;,
    evaluateAdjacency: (
        currentNode: Node&lt;N&gt;
    ) -&gt; Collection&lt;Node&lt;N&gt;&gt;,
    processNode: (
        currentNode: Node&lt;N&gt;, 
        adjacentNode: Node&lt;N&gt;
    ) -&gt; Node&lt;N&gt;,
    terminates: (
        currentNode: Node&lt;N&gt;
    ) -&gt; Boolean
): Int
            </code>
        </pre>
      </td>
      <td>
        Calculates the shortest distance to all nodes in a weighted-graph from the given starting Positions and terminates based on the given predicate.
      </td>
    </tr>
  </tbody>
</table>

## Test Support Library
### VisualVM Support
Running unit tests with JUnit via VisualVM can be difficult due to the time taken to launch the VisualVM process,
attach to the running Java thread, and configure sampling/profiling. There doesn't seem to be a particularly elegant
process for this other than telling the thread to wait.

One problem I ran into (and seemingly many others online too) was that JUnit5 bootstrapped and ran my test so quickly
that it finished before VisualVM even had a chance to boot-up. A common solution I'd seen was to simply add a
`Thread.sleep(x)` line at the start of the test method. Although this is the solution I technically went with, I
abstracted it into a [`@WaitForVisualVM`](https://git.io/JJdg1) annotation and created a custom implementation
of the Jupiter APIs [`BeforeTestExecutionCallback`](https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/extension/BeforeTestExecutionCallback.html)
interface called [`SupportsVisualVM.kt`](https://git.io/JJd2e) which can be added to a test-suite class using the
[`@ExtendWith`](https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/extension/ExtendWith.html) annotation.

This kept things inline with the 'enterprise-style' aspect of my codebase as it did the following;
- Wrapped the dubious `Thread.sleep()` call with a self-explanatory annotation (and is also documented).
- Removed noise from the test method and ensured that it always runs before test-execution.
- Allows developers to easily disable all waiting for tests in a suite by simply removing the support extension.
- Makes it easier to refactor in the future the as implementation specifics are encapsulated in the annotation.

## Release Instructions
* Make your code changes in the master branch (or feature branch)
* Update the release version in the build.gradle
* Rebase off of the release branch to ensure a fast-forward merge
* Merge to release branch once ready
* Create a new release on GitHub, set the new version tag and draft the release
* This will trigger GitHub actions release pipeline, wait for run to complete

## To-Do List
* Investigate and fix big integer printing, seems to be truncating
* A test support class for testing solutions. Accepts a solution and expected answers for p1, p2
* Added graph/node objets for graphing algos like Djikstra
* Added a findTile() function to the AdventMaps so you can pass a predicate to find or null
* Automatic scanning for Solution classes for the SolutionRunner
* AdventMap3D xMin() returns only where z=0? Can this be removed?
* AdventMap ordMin() methods use minByOrNull instead of minBy
* Move Formulae (lcm, gcd) from aoc-2019 to math package