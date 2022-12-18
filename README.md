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

## Contents
* [Implementation Library](#implementation-library)
  * [Logging](#logging)
  * [Input De-Serialisation](#input-de-serialisation)
  * [Math](#math)
  * [Solution Running & Benchmarking](#solution-running--benchmarking)
* [Test Support Library](#test-support-library)
  * [VisualVM Support](#visualvm-support)
* [Release Instructions](#release-instructions)

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
* Add error handling for input not found for solution runner
* Investigate and fix big integer printing, seems to be truncating
* Add init method to Map classes so you can pass a data set and have it parse (generic type for tile + predicate for mapping)
* A test support class for testing solutions. Accepts a solution and expected answers for p1, p2
* Added graph/node objets for graphing algos like Djikstra
* Added a findTile() function to the AdventMaps so you can pass a predicate to find or null
* Automatic scanning for Solution classes for the SolutionRunner