package io.github.tomplum.libs.math.equation

/**
 * Represents a system of two linear equations in the form:
 *
 *  a1 * x + b1 * y = c1
 *  a2 * x + b2 * y = c2
 *
 * This class provides a method to solve the system using Cramer's rule.
 *
 * @param a1 The coefficient of x in the first equation.
 * @param b1 The coefficient of y in the first equation.
 * @param c1 The constant term in the first equation.
 * @param a2 The coefficient of x in the second equation.
 * @param b2 The coefficient of y in the second equation.
 * @param c2 The constant term in the second equation.
 */
data class LinearEquation(
    val a1: Double,
    val b1: Double,
    val c1: Double,
    val a2: Double,
    val b2: Double,
    val c2: Double
) {
    /**
     * Solves the system of two linear equations using Cramer's rule.
     *
     * Cramer's rule states that for a system of two linear equations:
     *
     *  a1 * x + b1 * y = c1
     *  a2 * x + b2 * y = c2
     *
     * The solution is given by:
     *
     *  x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1)
     *  y = (a1 * c2 - a2 * c1) / (a1 * b2 - a2 * b1)
     *
     * If the determinant (a1 * b2 - a2 * b1) is 0, the system has no unique solution.
     *
     * @return A Pair of values (x, y) representing the solution to the system,
     *         or null if the system has no unique solution.
     */
    fun solve(): Pair<Double, Double>? {
        val determinant = a1 * b2 - a2 * b1

        // If determinant is zero, the system has no unique solution
        if (determinant == 0.0) {
            return null
        }

        val determinantX = c1 * b2 - c2 * b1
        val determinantY = a1 * c2 - a2 * c1

        val x = determinantX / determinant
        val y = determinantY / determinant

        return x to y
    }
}