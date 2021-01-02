package io.github.tomplum.libs.input.types

import io.github.tomplum.libs.input.InputReader

/**
 * A [Long] input wrapper returned by [InputReader].
 */
class LongInput(input: List<String>) : Input<Long>(input.map { it.toLong() })