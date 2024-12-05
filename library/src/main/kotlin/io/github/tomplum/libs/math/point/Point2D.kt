package io.github.tomplum.libs.math.point

import io.github.tomplum.libs.extensions.toRadians
import io.github.tomplum.libs.math.Direction
import kotlin.math.*

/**
 * A two-dimensional point in a cartesian co-ordinate system.
 * @see Point3D for a three-dimensional variant.
 *
 * @param x The x-ordinate of the point.
 * @param y The y-ordinate of the point.
 */
data class Point2D(val x: Int, val y: Int) : Point, Comparable<Point2D> {

    companion object {
        /**
         * The point where the axes intersect.
         * @return The origin point.
         */
        fun origin() = Point2D(0, 0)
    }

    /**
     * Adjacent points are the 8 points that are immediately surrounding the current point.
     * These include those on the horizontal, vertical and diagonal.
     * @see orthogonallyAdjacent for a function that returns just those on the horizontal and vertical.
     * @return The eight points that are adjacent.
     */
    override fun adjacent(): List<Point2D> {
        val orthogonal = orthogonallyAdjacent()
        val diagonal = listOf(Point2D(x - 1, y - 1), Point2D(x + 1, y - 1), Point2D(x + 1, y + 1), Point2D(x - 1, y + 1))
        return orthogonal + diagonal
    }

    /**
     * Orthogonally adjacent points are the 4 points immediately horizontal or vertical.
     * A.K.A 'Edge Adjacent'
     * @see adjacent for a function that returns on the diagonal too.
     * @return The four points that are orthogonally adjacent.
     */
    fun orthogonallyAdjacent() = listOf(Point2D(x, y + 1), Point2D(x + 1, y), Point2D(x, y - 1), Point2D(x - 1, y))

    /**
     * Diagonally adjacent points are the 4 points that are in the immediate
     * adjacent vicinity to the current point, excluding any orthogonally
     * adjacent points.
     *
     * In other words, it's the top right, bottom right, bottom left and top right
     * points relative to the current one.
     */
    fun diagonallyAdjacent(): List<Point2D> {
        return listOf(
            this.shift(Direction.TOP_RIGHT),
            this.shift(Direction.BOTTOM_RIGHT),
            this.shift(Direction.BOTTOM_LEFT),
            this.shift(Direction.TOP_LEFT)
        )
    }

    /**
     * Calculates the Manhattan Distance between two [Point2D]s.
     * The distance between the points is measured along the axes at right angles.
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
     * Rotates the point around the given [pivot] by the given [angle].
     * @param pivot The central point to rotate around.
     * @param angle The angle to rotate around the pivot by. Can be negative for counter-clockwise.
     * @return The translated [Point2D] after rotating it around the [pivot].
     */
    fun rotateAbout(pivot: Point2D, angle: Int = 90): Point2D {
        val normalisedAngle = if (angle < 0) angle + 360 else angle

        val sin = sin(normalisedAngle.toDouble().toRadians())
        val cos = cos(normalisedAngle.toDouble().toRadians())

        val x1 = x - pivot.x
        val y1 = y - pivot.y

        val x2 = x1 * cos + y1 * sin
        val y2 = -x1 * sin + y1 * cos

        val xNew = x2 + pivot.x
        val yNew = y2 + pivot.y

        return Point2D(xNew.roundToInt(), yNew.roundToInt())
    }

    /**
     * Compares this point to the [other] and returns the relative horizontal direction and the distance between them.
     * E.g. A(1, 2) and B(5, 3). A.xRelativeDirection(B) would return (LEFT, 4) because A is to-the-left of B
     *      with a distance of 4. Therefore, B.xRelativeDirection(A) would return (RIGHT, 4).
     * @param other The relative point to compare against.
     * @return The relative direction from the [other] point and the absolute distance between them.
     */
    fun xRelativeDirection(other: Point2D): Pair<Direction, Int>? {
        val xDelta = x - other.x
        return when {
            xDelta > 0 -> Pair(Direction.RIGHT, xDelta)
            xDelta < 0 -> Pair(Direction.LEFT, abs(xDelta))
            else -> null
        }
    }

    /**
     * Compares this point to the [other] and returns the relative vertical direction and the distance between them.
     * E.g. A(1, 2) and B(5, 3). A.yRelativeDirection(B) would return (DOWN, 1) because A is below B
     *      with a distance of 1. Therefore, B.yRelativeDirection(A) would return (UP, 1).
     * @param other The relative point to compare against.
     * @return The relative direction from the [other] point and the absolute distance between them.
     */
    fun yRelativeDirection(other: Point2D): Pair<Direction, Int>? {
        val yDelta = y - other.y
        return when {
            yDelta > 0 -> Pair(Direction.UP, yDelta)
            yDelta < 0 -> Pair(Direction.DOWN, abs(yDelta))
            else -> null
        }
    }

    /**
     * Calculates the direction in which the [other] point is
     * in relation to this [Point2D] instance. Also returns
     * the x and y distance to the other point in the form of
     * another [Point2D] instance.
     *
     * @param other The point to check the relative direction of.
     * @return The relative direction and coordinate distances.
     */
    fun directionTo(other: Point2D): Pair<Direction?, Point2D> {
        val xDelta = other.x - x
        val yDelta = other.y - y
        val distance = Point2D(abs(xDelta), abs(yDelta))

        return when {
            yDelta > 0 && xDelta == 0 -> Direction.UP
            yDelta < 0 && xDelta == 0 -> Direction.DOWN
            xDelta > 0 && yDelta == 0 -> Direction.RIGHT
            xDelta < 0 && yDelta == 0 -> Direction.LEFT
            yDelta < 0 && xDelta > 0 -> Direction.BOTTOM_RIGHT
            yDelta > 0 && xDelta < 0 -> Direction.TOP_LEFT
            yDelta < 0 -> Direction.BOTTOM_LEFT
            yDelta > 0 -> Direction.TOP_RIGHT
            else -> null
        }.let { direction ->
            Pair(direction, distance)
        }
    }

    /**
     * @return The number of points away from the x-axis
     */
    fun distanceFromAxisX(): Int = abs(0 - x)

    /**
     * @return The number of points away from the y-axis
     */
    fun distanceFromAxisY(): Int = abs(0 - y)

    /**
     * Compares this [Point2D] instance with the [other].
     * @param other The other point to compare with
     */
    override fun compareTo(other: Point2D): Int {
        return if (this.y != other.y) {
            this.y.compareTo(other.y)
        } else {
            this.x.compareTo(other.x)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Point2D) return false
        return this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int = x.hashCode() + y.hashCode()

    override fun toString() = "($x, $y)"
}
