package io.github.tomplum.libs.math.map

import io.github.tomplum.libs.math.point.Point2D
import io.github.tomplum.libs.math.point.Point3D
import io.github.tomplum.libs.math.point.Point4D

/**
 * This class is designed for inheritance.
 *
 * Lots of the days involve the concept of a 'Map' or a 'Maze' in which the shortest path must be found while
 * meeting day-specific criteria. A cartesian-style graph is internally maintained that maps tiles to [Point4D]
 * coordinates.
 *
 * This class is very similar to [AdventMap2D] and [AdventMap3D].
 * The major difference is the internal data structure maps [Point2D] and [Point3D].
 *
 * @param T The type of 'tile' that will be mapped.
 */
abstract class AdventMap4D<T> {
    /** The internal data representation, mapping the positions to the tiles */
    private val data = mutableMapOf<Point4D, T>()

    /**
     * Adds a new [tile] at the given [position].
     * If a tile already exists at the given position then it returned, otherwise null.
     */
    protected fun addTile(position: Point4D, tile: T): T? = data.put(position, tile)

    /**
     * Retrieves the tile at the given [position].
     * @throws IllegalArgumentException if the map does not contain a tile at the given [position]
     */
    protected fun getTile(position: Point4D): T =
        data[position] ?: throw IllegalArgumentException("Map does not contain tile at $position")

    /**
     * @return true if the map has recorded a tile at the given [position]
     */
    protected fun hasRecorded(position: Point4D): Boolean = data.containsKey(position)

    /**
     * Gets all the tiles at the given [positions]. If there is no recorded at tile at one of the given [positions]
     * then it is omitted from the response.
     * @return a [Map] of the given [positions] and their respective tiles.
     */
    protected fun filterPoints(positions: Set<Point4D>): Map<Point4D, T> =
        positions.filter(this::hasRecorded).associateWith(this::getTile)

    /**
     * Gets all the tiles that equate to true on the given [predicate].
     * Each implementation of [AdventMap3D] will have a tile of type [T]. This tile will provide the function
     * that will be evaluated in this predicate.
     * @return a [Map] of all tiles that match the given [predicate].
     */
    protected fun filterTiles(predicate: (T) -> Boolean): Map<Point4D, T> = data.filterValues(predicate)

    /**
     * @return The minimum x-ordinate currently recorded in the map.
     */
    protected open fun xMin() = data.keys.filter { it.z == 0 }.minByOrNull { it.x }?.x

    /**
     * @return The minimum y-ordinate currently recorded in the map.
     */
    protected open fun yMin() = data.keys.minByOrNull { it.y }?.y

    /**
     * @return The minimum z-ordinate currently recorded in the map.
     */
    protected fun zMin() = data.keys.minByOrNull { it.z }?.z

    /**
     * @return The minimum w-ordinate currently recorded in the map.
     */
    protected fun wMin() = data.keys.minByOrNull { it.w }?.w

    /**
     * @return The maximum x-ordinate currently recorded in the map.
     */
    protected open fun xMax() = data.keys.filter { it.z == 0 }.maxByOrNull { it.x }?.x

    /**
     * @return The maximum y-ordinate currently recorded in the map.
     */
    protected open fun yMax() = data.keys.maxByOrNull { it.y }?.y

    /**
     * @return The maximum y-ordinate currently recorded in the map.
     */
    protected fun zMax() = data.keys.maxByOrNull { it.z }?.z

    /**
     * @return The maximum w-ordinate currently recorded in the map.
     */
    protected fun wMax() = data.keys.maxByOrNull { it.w }?.w

    
    /**
     * Creates a cartesian graph style visual representation of the [data].
     */
    override fun toString(): String {
        val yMin = yMin() ?: 0
        val yMax = yMax() ?: 0
        val xMin = xMin() ?: 0
        val xMax = xMax() ?: 0
        val zMin = zMin() ?: 0
        val zMax = zMax() ?: 0
        val wMin = wMin() ?: 0
        val wMax = wMax() ?: 0


        return (wMin..wMax).joinToString("\n") { w ->
            (zMin..zMax).joinToString("\n") { z ->
                (yMin..yMax).joinToString("\n") { y ->
                    (xMin..xMax).joinToString(" ") { x ->
                        data.getOrDefault(Point4D(x, y, z, w), " ").toString()
                    }
                }.plus("\n")
            }
        }
    }
}