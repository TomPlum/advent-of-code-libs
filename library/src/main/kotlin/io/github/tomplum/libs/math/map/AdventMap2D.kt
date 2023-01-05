package io.github.tomplum.libs.math.map

import io.github.tomplum.libs.math.point.Point2D

/**
 * This class is designed for inheritance.
 *
 * Lots of the days involve the concept of a 'Map' or a 'Maze' in which the shortest path must be found while
 * meeting day-specific criteria. A cartesian-style graph is internally maintained that maps tiles to [Point2D]
 * coordinates.
 *
 * @param T The type of [MapTile] that will be mapped.
 */
abstract class AdventMap2D<T: MapTile<*>>: AdventMap<Point2D, T>() {

    /**
     * Initialises the map based on the given [data].
     * The [data] parameter should be a list of [String]
     * where each character represents a value in the grid.
     *
     * @param data A collection of row data of the map
     * @param tileConstructor Constructs a new map tile with the given value
     */
    protected fun init(data: Collection<String>, tileConstructor: (value: Any) -> T) {
        var x = 0
        var y = 0
        data.forEach { row ->
            row.forEach { column ->
                val tile = tileConstructor(column)
                val position = Point2D(x, y)
                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    /**
     * Gets all the tiles that are orthogonally adjacent to the given [positions].
     * @param positions The set of positions whose adjacent tiles will be found.
     * @return a [Map] of orthogonally-adjacent [Point2D] and their respective tiles [T].
     */
    protected fun adjacentTilesOrthogonal(positions: Set<Point2D>): Map<Point2D, T> {
        return positions.flatMap { pos -> pos.orthogonallyAdjacent() }.associateWith(this::getTile)
    }

    /**
     * @return The minimum x-ordinate currently recorded in the map.
     */
    protected fun xMin(): Int? = data.keys.minByOrNull { pos -> pos.x }?.x

    /**
     * @return The minimum y-ordinate currently recorded in the map.
     */
    protected fun yMin(): Int? = data.keys.minByOrNull { pos -> pos.y }?.y

    /**
     * @return The maximum x-ordinate currently recorded in the map.
     */
    protected fun xMax(): Int? = data.keys.maxByOrNull { pos -> pos.x }?.x

    /**
     * @return The maximum y-ordinate currently recorded in the map.
     */
    protected fun yMax(): Int? = data.keys.maxByOrNull { pos -> pos.y }?.y

    /**
     * Creates a cartesian graph style visual representation of the [data]
     */
    override fun toString(): String {
        val yMin = yMin() ?: 0
        val yMax = yMax() ?: 0
        val xMin = xMin() ?: 0
        val xMax = xMax() ?: 0
        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString(" ") { x ->
                data.getOrDefault(Point2D(x, y), " ").toString()
            }
        }.plus("\n")
    }
}