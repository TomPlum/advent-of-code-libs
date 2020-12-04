# :christmas_tree: Advent of Code Library

![GitHub](https://img.shields.io/github/license/TomPlum/advent-of-code-2020?color=informational)
![GitHub](https://img.shields.io/badge/instructions-98%25-success)
![GitHub](https://img.shields.io/badge/branches-93%25-orange)

## About
* [Logging](#logging)
* [Input De-Serialisation](#input-de-serialisation)
* [Math](#math)

### Logging
The [AdventLogger](https://git.io/JILT9) provides a simple companion-object class that wraps the
[SLF4J](http://www.slf4j.org/) abstraction. The class exposes the usual levels which are as follows

| Level | Description                                                                                                |
|-------|------------------------------------------------------------------------------------------------------------|
| Error | Designates error events that might still allow the application to continue running.                        |
| Warn  | Designates potentially harmful situations.                                                                 |
| Info  | Designates information messages that highlight the progress of the application at a coarse-grained level.  |
| Debug | Designates fine-grained informational events that are most useful to debug the application.                |
| Trace | Designates finer-grained informational events than the debug level.                                        |

### Input De-Serialisation
The [InputReader](https://git.io/JILkc) provides an easy way to read the puzzle inputs from the `resources` directory
of the main source set. The reader looks for a file named `input.txt` in a sub-folder named `dayX` where `X` is the day 
number. The `read()` function also expects a generic type parameter which changes the type of collection that is returned.
The currently supported types are `Integer` and `String`.

### Math
A common theme in the Advent of Code is maps, which are usually represented on a 2D cartesian grid. Thus,
[Point2D](https://git.io/JImDn) is a wrapper class for co-ordinates that provides useful translation, transformation
and interacting with other points. Likewise, [Point3D](https://git.io/JImDz) does a similar thing for 3-Dimensions.

To accompany the `Point` classes, [AdventMap2D]() and [AdventMap3D]() have been created for `Point2D` and `Point3D`
respectively. These classes are essentially cartesian grids backed by a `Map` of the respective `Point` class against
a [MapTile]() which accepts a generic type. This is usually a `Char` due to the nature of Advent of Code puzzle inputs.

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