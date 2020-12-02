package io.github.tomplum.libs.input.types

import io.github.tomplum.libs.input.InputReader

/**
 * An Integer input wrapper returned by [InputReader].
 */
class IntegerInput(input: List<String>) : Input<Int>(input.map { it.toInt() })