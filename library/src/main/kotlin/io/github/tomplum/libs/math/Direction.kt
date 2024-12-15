package io.github.tomplum.libs.math

/**
 * The relative direction at given angle from the observer.
 * @param degree The degree of the angle. A multiple of 45 between 0 and 360.
 */
enum class Direction(private val degree: Int) {
    UP(0),
    TOP_RIGHT(45),
    RIGHT(90),
    BOTTOM_RIGHT(135),
    DOWN(180),
    BOTTOM_LEFT(225),
    LEFT(270),
    TOP_LEFT(315);

    companion object {
        fun fromChar(value: Char) = when (value) {
            '^' -> UP
            '>' -> RIGHT
            'v' -> DOWN
            '<' -> LEFT
            else -> throw IllegalArgumentException("Invalid Direction String: $value")
        }
    }

    /**
     * Rotates the current direction by the given [angle].
     * Angles can be negative to rotate anti-clockwise.
     *
     * @param angle A multiple of 45 between 0 and 360. Can be positive or negative.
     * @throws IllegalArgumentException if the [angle] is invalid.
     * @return The new facing direction after rotating by the given [angle].
     */
    fun rotate(angle: Int): Direction = entries
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