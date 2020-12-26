package io.github.tomplum.libs.math.map

import io.github.tomplum.libs.math.point.Point
import io.github.tomplum.libs.math.point.Point2D
import io.github.tomplum.libs.math.point.Point3D

/**
 * This class is designed for inheritance.
 *
 * Lots of the days involve the concept of a 'Map' or a 'Maze' in which the shortest path must be found while
 * meeting day-specific criteria. A cartesian-style graph is internally maintained that maps tiles to [Point3D]
 * coordinates.
 *
 * This class is very similar to [AdventMap2D]. The major difference is the internal data structure maps [Point3D]
 * instead of [Point2D].
 *
 * @param T The type of 'tile' that will be mapped.
 * @see AdventMap2D
 */
abstract class AdventMap3D<T: MapTile<*>>: AdventMap<T>() {
    /**
     * Gets all the tiles that are adjacent to the given [positions] on the same plane.
     * @param positions The positions of the target tiles.
     * @return a [Map] of adjacent [Point3D] and their respective tiles, [T].
     */
    protected fun planarAdjacentTiles(positions: Set<Point3D>): Map<Point, T> {
        return positions.flatMap { pos -> pos.planarAdjacentPoints() }.associateWith(this::getTile)
    }

    /**
     * Duplicates the top-layer (where z = 0) [n] times in the upwards direction.
     */
    protected fun duplicateTopLayer(n: Int) {
        val topLayer = data.entries
        val nIterator = (1..n).iterator()
        val toAdd = mutableMapOf<Point3D, T>()
        while (nIterator.hasNext()) {
            val it = topLayer.iterator()
            val z = nIterator.nextInt()
            while (it.hasNext()) {
                val next = it.next()
                val position = next.key as Point3D
                toAdd[Point3D(position.x, position.y, z)] = next.value
            }
        }
        data.putAll(toAdd)
    }

    /**
     * @return The minimum x-ordinate currently recorded in the map.
     */
    protected open fun xMin() = data.keys.map { it as Point3D }.filter { it.z == 0 }.minByOrNull { it.x }?.x

    /**
     * @return The minimum y-ordinate currently recorded in the map.
     */
    protected open fun yMin() = data.keys.map { it as Point3D }.minByOrNull { it.y }?.y

    /**
     * @return The minimum z-ordinate currently recorded in the map.
     */
    protected fun zMin() = data.keys.map { it as Point3D }.minByOrNull { it.z }?.z

    /**
     * @return The maximum x-ordinate currently recorded in the map.
     */
    protected open fun xMax() = data.keys.map { it as Point3D }.filter { it.z == 0 }.maxByOrNull { it.x }?.x

    /**
     * @return The maximum y-ordinate currently recorded in the map.
     */
    protected open fun yMax() = data.keys.map { it as Point3D }.maxByOrNull { it.y }?.y

    /**
     * @return The maximum y-ordinate currently recorded in the map.
     */
    protected fun zMax() = data.keys.map { it as Point3D }.maxByOrNull { it.z }?.z

    /**
     * Creates a cartesian graph style visual representation of the [data] at the top-level where z = 0.
     */
    fun toStringTopLevel(): String {
        val yMin = yMin() ?: 0
        val yMax = yMax() ?: 0
        val xMin = xMin() ?: 0
        val xMax = xMax() ?: 0
        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString(" ") { x ->
                data.getOrDefault(Point3D(x, y, 0), " ").toString()
            }
        }.plus("\n")
    }

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
        return (zMin..zMax).joinToString("\n") { z ->
            (yMin..yMax).joinToString("\n") { y ->
                (xMin..xMax).joinToString(" ") { x ->
                    data.getOrDefault(Point3D(x, y, z), " ").toString()
                }
            }.plus("\n")
        }
    }
}