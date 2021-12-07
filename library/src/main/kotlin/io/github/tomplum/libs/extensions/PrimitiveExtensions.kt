package io.github.tomplum.libs.extensions

import kotlin.math.pow

/**
 * Converts an [Int] into its binary equivalent.
 * Pads the number with trailing 0s to reach the number of given [bits].
 * @param bits The number of bits.
 * @return An array of bits representing the integer in binary.
 */
fun Int.toBinary(bits: Int): IntArray = Integer.toBinaryString(this)
    .padStart(bits, '0')
    .foldIndexed(IntArray(bits)) { i, arr, value ->
        arr.apply { set(i, value.toString().toInt()) }
    }

/**
 * Converts an angle in degrees into radians.
 * @return The value in radians.
 */
fun Double.toRadians() = this / 180 * Math.PI

/**
 * Calculate the value at the given position [this] in Pascal's triangle.
 * This can be expressed as (n^2 + n) / 2 as a binomial coefficient.
 * @return The value at the given position.
 */
fun Int.nthBinomialCoefficient() = ((this.toDouble().pow(2) + this) / 2).toInt()
