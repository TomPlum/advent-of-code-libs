package io.github.tomplum.libs.math.equation

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.Test

class LinearEquationTest {
    @Test
    fun `solves system with unique solution`() {
        val equation = LinearEquation(2.0, 1.0, 8.0, 1.0, 3.0, 13.0)
        val result = equation.solve()

        assertThat(result).isEqualTo(2.2 to 3.6)
    }

    @Test
    fun `returns null for no unique solution`() {
        val equation = LinearEquation(1.0, -1.0, 0.0, 2.0, -2.0, 0.0)
        val result = equation.solve()

        assertThat(result).isNull()
    }

    @Test
    fun `solves system with negative coefficients`() {
        val equation = LinearEquation(-1.0, -2.0, -3.0, -3.0, -4.0, -5.0)
        val result = equation.solve()

        assertThat(result).isEqualTo(-1.0 to 2.0)
    }

    @Test
    fun `solves system with fractional coefficients`() {
        val equation = LinearEquation(0.5, 1.5, 3.0, 1.5, 0.5, 4.0)
        val result = equation.solve()

        assertThat(result).isEqualTo(2.25 to 1.25)
    }
}