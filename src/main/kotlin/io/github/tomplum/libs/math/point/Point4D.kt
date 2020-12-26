package io.github.tomplum.libs.math.point

/**
 * A Four-Dimensional Point
 * @param x The x-ordinate
 * @param y The y-ordinate
 * @param z The z-ordinate
 * @param w The w-ordinate
 */
data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) : Point {

    /**
     * Returns the 80 points that are adjacent to the current point.
     * @return The 80 adjacent points.
     */
    override fun adjacent(): List<Point4D> {
        val thirdDimension = getThirdDimension()
        val third = thirdDimension.adjacent().map { Point4D(it.x, it.y, it.z, w) }
        val above = Point4D(x, y, z, w + 1)
        val abovePlane = above.getThirdDimension().adjacent().map { Point4D(it.x, it.y, it.z, w + 1) }
        val below = Point4D(x, y, z, w - 1)
        val belowPlane = below.getThirdDimension().adjacent().map { Point4D(it.x, it.y, it.z, w - 1) }
        return third + above + abovePlane + below + belowPlane
    }

    private fun getThirdDimension() = Point3D(x, y, z)
}