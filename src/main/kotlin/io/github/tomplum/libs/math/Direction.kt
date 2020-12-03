package io.github.tomplum.libs.math

enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    /**
     * Rotates the current [Direction] by 90deg.
     */
    fun rotateClockwise(): Direction = when(this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }

    /**
     * Rotates the current [Direction] by -90deg.
     */
    fun rotateAntiClockwise(): Direction = when(this) {
        UP -> LEFT
        LEFT -> DOWN
        DOWN -> RIGHT
        RIGHT -> UP
    }
}