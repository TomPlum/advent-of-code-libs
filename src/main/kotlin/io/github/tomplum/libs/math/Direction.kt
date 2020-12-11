package io.github.tomplum.libs.math

enum class Direction {
    UP, RIGHT, DOWN, LEFT,
    TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT;

    /**
     * Rotates the current [Direction] by 90deg.
     */
    fun rotateClockwise90(): Direction = when(this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
        TOP_RIGHT -> BOTTOM_RIGHT
        BOTTOM_RIGHT -> BOTTOM_LEFT
        BOTTOM_LEFT -> TOP_LEFT
        TOP_LEFT -> TOP_RIGHT
    }

    /**
     * Rotates the current [Direction] by -90deg.
     */
    fun rotateAntiClockwise(): Direction = when(this) {
        UP -> LEFT
        LEFT -> DOWN
        DOWN -> RIGHT
        RIGHT -> UP
        TOP_RIGHT -> TOP_LEFT
        TOP_LEFT -> BOTTOM_LEFT
        BOTTOM_LEFT -> BOTTOM_RIGHT
        BOTTOM_RIGHT -> TOP_RIGHT
    }
}