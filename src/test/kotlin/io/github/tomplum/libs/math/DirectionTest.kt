package io.github.tomplum.libs.math

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class DirectionTest {
    @Test
    fun rotateClockwiseStartingUp() {
        val up = Direction.UP
        val newDirection = up.rotateClockwise()
        assertThat(newDirection).isEqualTo(Direction.RIGHT)
    }

    @Test
    fun rotateClockwiseStartingRight() {
        val up = Direction.RIGHT
        val newDirection = up.rotateClockwise()
        assertThat(newDirection).isEqualTo(Direction.DOWN)
    }

    @Test
    fun rotateClockwiseStartingDown() {
        val up = Direction.DOWN
        val newDirection = up.rotateClockwise()
        assertThat(newDirection).isEqualTo(Direction.LEFT)
    }

    @Test
    fun rotateClockwiseStartingLeft() {
        val up = Direction.LEFT
        val newDirection = up.rotateClockwise()
        assertThat(newDirection).isEqualTo(Direction.UP)
    }

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
}