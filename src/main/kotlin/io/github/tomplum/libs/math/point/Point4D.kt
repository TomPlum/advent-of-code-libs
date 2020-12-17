package io.github.tomplum.libs.math.point

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun adjacent(): List<Point4D> {
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