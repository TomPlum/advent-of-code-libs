package io.github.tomplum.libs.input.types

import io.github.tomplum.libs.input.InputReader

/**
 * A wrapper class for a puzzle input.
 * @see InputReader
 */
abstract class Input<T>(val value: List<T>) {
    fun asSingleString() = value.joinToString(separator = "\n")
}