package io.github.tomplum.libs.math

import kotlin.math.abs
import kotlin.math.atan2

/**
 * A Two-Dimensional Point
 */
data class Point2D(val x: Int, val y: Int) {

    /**
     * Returns the four points that are orthogonally adjacent.
     */
    fun adjacentPoints() = listOf(Point2D(x, y + 1), Point2D(x + 1, y), Point2D(x, y - 1), Point2D(x - 1, y))

    /**
     * Calculates the Manhattan Distance between two [Point2D]
     */
    fun distanceBetween(point: Point2D): Int = abs(this.x - point.x) + abs(this.y - point.y)

    /**
     * Calculates the positive clockwise angle between two [Point2D] in degrees.
     * Angles are calculated from the sector's true north in the range of 0 =< angle < 360.
     */
    fun angleBetween(point: Point2D): Double {
        val angle = atan2((y - point.y).toDouble(), (x - point.x).toDouble()) * (180 / Math.PI) - 90.00
        return if (angle < 0) angle + 360.00 else angle
    }

    /**
     * Checks if the one point is orthogonally adjacent to another.
     * @return true if adjacent to [that] point, else false.
     */
    fun isAdjacentTo(that: Point2D): Boolean = this != that && abs(x - that.x) <= 1 && abs(y - that.y) <= 1

    /**
     * Shifts the [Point2D] one unit in the given [direction].
     * E.g. (0, 0) shifted [Direction.RIGHT] would become (1, 0)
     */
    fun shift(direction: Direction): Point2D = when (direction) {
        Direction.UP -> Point2D(x, y + 1)
        Direction.RIGHT -> Point2D(x + 1, y)
        Direction.DOWN -> Point2D(x, y - 1)
        Direction.LEFT -> Point2D(x - 1, y)
    }

    /**
     * @return The number of points away from the x-axis
     */
    fun distanceFromAxisX(): Int = abs(0 - x)

    /**
     * @return The number of points away from the y-axis
     */
    fun distanceFromAxisY(): Int = abs(0 - y)

    override fun equals(other: Any?): Boolean {
        if (other !is Point2D) return false
        return this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int = x.hashCode() + y.hashCode()

    override fun toString() = "($x, $y)"
}