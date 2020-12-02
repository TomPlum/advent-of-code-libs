# :christmas_tree: Advent of Code Library

## Functionality

### Logging
The [AdventLogger](https://git.io/JILT9) provides a simple companion-object class that wraps the
[SLF4J](http://www.slf4j.org/) abstraction.

### Input De-Serialisation
The [InputReader](https://git.io/JILkc) provides an easy way to read the puzzle inputs from the `resources` directory
of the main source set. The reader looks for a file named `input.txt` in a sub-folder named `dayX` where `X` is the day 
number. The `read()` function also expects a generic type parameter which changes the type of collection that is returned.
The currently supported types are `Integer` and `String`.