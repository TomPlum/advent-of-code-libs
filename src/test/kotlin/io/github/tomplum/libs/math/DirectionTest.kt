package io.github.tomplum.libs.math

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DirectionTest {
    @Nested
    inner class RotateClockwise90 {
        @Test
        fun rotateClockwiseStartingUp() {
            val up = Direction.UP
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.RIGHT)
        }

        @Test
        fun rotateClockwiseStartingRight() {
            val up = Direction.RIGHT
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.DOWN)
        }

        @Test
        fun rotateClockwiseStartingDown() {
            val up = Direction.DOWN
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.LEFT)
        }

        @Test
        fun rotateClockwiseStartingLeft() {
            val up = Direction.LEFT
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.UP)
        }

        @Test
        fun rotateClockwiseStartingTopRight() {
            val up = Direction.TOP_RIGHT
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.BOTTOM_RIGHT)
        }

        @Test
        fun rotateClockwiseStartingBottomRight() {
            val up = Direction.BOTTOM_RIGHT
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.BOTTOM_LEFT)
        }

        @Test
        fun rotateClockwiseStartingBottomLeft() {
            val up = Direction.BOTTOM_LEFT
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.TOP_LEFT)
        }

        @Test
        fun rotateClockwiseStartingTopLeft() {
            val up = Direction.TOP_LEFT
            val newDirection = up.rotateClockwise90()
            assertThat(newDirection).isEqualTo(Direction.TOP_RIGHT)
        }
    }

    @Nested
    inner class RotateAntiClockwise90 {
        @Test
        fun rotateAntiClockwiseStartingUp() {
            val up = Direction.UP
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.LEFT)
        }

        @Test
        fun rotateAntiClockwiseStartingRight() {
            val up = Direction.RIGHT
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.UP)
        }

        @Test
        fun rotateAntiClockwiseStartingDown() {
            val up = Direction.DOWN
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.RIGHT)
        }

        @Test
        fun rotateAntiClockwiseStartingLeft() {
            val up = Direction.LEFT
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.DOWN)
        }

        @Test
        fun rotateAntiClockwiseStartingTopLeft() {
            val up = Direction.TOP_LEFT
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.BOTTOM_LEFT)
        }

        @Test
        fun rotateAntiClockwiseStartingBottomLeft() {
            val up = Direction.BOTTOM_LEFT
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.BOTTOM_RIGHT)
        }

        @Test
        fun rotateAntiClockwiseStartingBottomRight() {
            val up = Direction.BOTTOM_RIGHT
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.TOP_RIGHT)
        }

        @Test
        fun rotateAntiClockwiseStartingTopRight() {
            val up = Direction.TOP_RIGHT
            val newDirection = up.rotateAntiClockwise()
            assertThat(newDirection).isEqualTo(Direction.TOP_LEFT)
        }
    }
}