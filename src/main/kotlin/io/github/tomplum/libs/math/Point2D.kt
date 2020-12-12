package io.github.tomplum.libs.math

import kotlin.math.abs
import kotlin.math.atan2

/**
 * A Two-Dimensional Point
 */
data class Point2D(val x: Int, val y: Int) {

    /**
     * Orthogonally adjacent points are the 4 points immediately horizontal or vertical.
     * A.K.A 'Edge Adjacent'
     * @see adjacent for a function that returns on the diagonal too.
     * @return The four points that are orthogonally adjacent.
     */
    fun orthogonallyAdjacent() = listOf(Point2D(x, y + 1), Point2D(x + 1, y), Point2D(x, y - 1), Point2D(x - 1, y))

    /**
     * Adjacent points are the 8 points that are immediately surrounding the current point.
     * These include those on the horizontal, vertical and diagonal.
     * @see orthogonallyAdjacent for a function that returns just those on the horizontal and vertical.
     * @return The eight points that are adjacent.
     */
    fun adjacent(): List<Point2D> {
        val orthogonal = orthogonallyAdjacent()
        val diagonal = listOf(Point2D(x - 1, y - 1), Point2D(x + 1, y - 1), Point2D(x + 1, y + 1), Point2D(x - 1, y + 1))
        return orthogonal + diagonal
    }

    /**
     * Calculates the Manhattan Distance between two [Point2D]s.
     * The distance between the points is measured along the axes at right angled.
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
     * @see orthogonallyAdjacent
     * @return true if adjacent to [that] point, else false.
     */
    fun isOrthogonallyAdjacentTo(that: Point2D): Boolean = this != that && abs(x - that.x) <= 1 && abs(y - that.y) <= 1

    /**
     * Shifts the [Point2D] one unit in the given [direction] unless specified by the [units] parameter.
     * E.g. (0, 0) shifted [Direction.RIGHT] would become (1, 0)
     * @return A point at the shifted location.
     */
    fun shift(direction: Direction, units: Int = 1): Point2D = when (direction) {
        Direction.UP -> Point2D(x, y + units)
        Direction.RIGHT -> Point2D(x + units, y)
        Direction.DOWN -> Point2D(x, y - units)
        Direction.LEFT -> Point2D(x - units, y)
        Direction.TOP_RIGHT -> Point2D(x + units, y + units)
        Direction.BOTTOM_RIGHT -> Point2D(x + units, y - units)
        Direction.BOTTOM_LEFT -> Point2D(x - units, y - units)
        Direction.TOP_LEFT -> Point2D(x - units, y + units)
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