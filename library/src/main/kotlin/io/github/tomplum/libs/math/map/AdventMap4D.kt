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
abstract class AdventMap4D<T: MapTile<*>>: AdventMap<Point4D, T>() {
    /**
     * @return The minimum x-ordinate currently recorded in the map.
     */
    protected open fun xMin() = data.keys.filter { pos -> pos.z == 0 }.minByOrNull { pos -> pos.x }?.x

    /**
     * @return The minimum y-ordinate currently recorded in the map.
     */
    protected open fun yMin() = data.keys.minByOrNull { pos -> pos.y }?.y

    /**
     * @return The minimum z-ordinate currently recorded in the map.
     */
    protected fun zMin() = data.keys.minByOrNull { pos -> pos.z }?.z

    /**
     * @return The minimum w-ordinate currently recorded in the map.
     */
    protected fun wMin() = data.keys.minByOrNull { pos -> pos.w }?.w

    /**
     * @return The maximum x-ordinate currently recorded in the map.
     */
    protected open fun xMax() = data.keys.filter { pos -> pos.z == 0 }.maxByOrNull { pos -> pos.x }?.x

    /**
     * @return The maximum y-ordinate currently recorded in the map.
     */
    protected open fun yMax() = data.keys.maxByOrNull { pos -> pos.y }?.y

    /**
     * @return The maximum y-ordinate currently recorded in the map.
     */
    protected fun zMax() = data.keys.maxByOrNull { pos -> pos.z }?.z

    /**
     * @return The maximum w-ordinate currently recorded in the map.
     */
    protected fun wMax() = data.keys.maxByOrNull { pos -> pos.w }?.w

    
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