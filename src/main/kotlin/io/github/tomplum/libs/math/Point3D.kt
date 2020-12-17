package io.github.tomplum.libs.math

/**
 * A Three-Dimensional Point
 */
data class Point3D(val x: Int, val y: Int, val z: Int) {

    /**
     * Returns the 4 points that are orthogonally adjacent with the current two-dimensional plane.
     * @see Point2D.orthogonallyAdjacent
     */
    fun planarAdjacentPoints(): List<Point3D> = getSecondDimension().orthogonallyAdjacent().map { Point3D(it.x, it.y, z) }

    /**
     * Returns the 26 points that are adjacent to the current point.
     * These include the 8 surrounding the point in its current plane.
     * The 9 in the plane above, and the 9 in the plane below.
     * @return The 26 adjacent points.
     */
    fun adjacent(): List<Point3D> {
        val secondDimension = getSecondDimension()
        val planar = secondDimension.adjacent().map { Point3D(it.x, it.y, z) }
        val above = Point3D(x, y, z + 1)
        val abovePlane = above.getSecondDimension().adjacent().map { Point3D(it.x, it.y, z + 1) }
        val below = Point3D(x, y, z - 1)
        val belowPlane = below.getSecondDimension().adjacent().map { Point3D(it.x, it.y, z - 1) }
        return planar + above + abovePlane + below + belowPlane
    }

    /**
     * @return true if [that] is orthogonally adjacent within the current two-dimensional plane.
     * @see Point2D.isOrthogonallyAdjacentTo
     */
    fun isPlanarAdjacentTo(that: Point3D): Boolean = getSecondDimension().isOrthogonallyAdjacentTo(Point2D(that.x, that.y)) && z == that.z

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