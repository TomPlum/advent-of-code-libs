package io.github.tomplum.libs.math

enum class Direction(private val degree: Int) {
    UP(0),
    TOP_RIGHT(45),
    RIGHT(90),
    BOTTOM_RIGHT(135),
    DOWN(180),
    BOTTOM_LEFT(225),
    LEFT(270),
    TOP_LEFT(315);

    fun rotate(angle: Int): Direction = values()
        .find { it.degree == normalise(angle) }
        ?: throw IllegalArgumentException("Invalid Angle $angle")

    private fun normalise(angle: Int): Int {
        var targetDegree = degree + angle
        if (targetDegree >= 360) {
            targetDegree -= 360
        }
        if (targetDegree < 0) {
            targetDegree += 360
        }
        return targetDegree
    }

}