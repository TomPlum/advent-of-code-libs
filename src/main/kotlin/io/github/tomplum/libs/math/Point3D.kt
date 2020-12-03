package io.github.tomplum.libs.math

/**
 * A Three-Dimensional Point
 */
data class Point3D(val x: Int, val y: Int, val z: Int) {

    /**
     * Returns the 4 points that are orthogonally adjacent with the current two-dimensional plane.
     * @see Point2D.adjacentPoints
     */
    fun planarAdjacentPoints(): List<Point3D> = getSecondDimension().adjacentPoints().map { Point3D(it.x, it.y, z) }

    /**
     * @return true if [that] is orthogonally adjacent within the current two-dimensional plane.
     * @see Point2D.isAdjacentTo
     */
    fun isPlanarAdjacentTo(that: Point3D): Boolean = getSecondDimension().isAdjacentTo(Point2D(that.x, that.y)) && z == that.z

    /**
     * @see Point2D.distanceFromAxisX
     */
    fun distanceFromAxisX() = getSecondDimension().distanceFromAxisX()

    /**
     * @see Point2D.distanceFromAxisY
     */
    fun distanceFromAxisY() = getSecondDimension().distanceFromAxisY()

    private fun getSecondDimension() = Point2D(x, y)

    override fun toString() = "($x, $y, $z)"
}