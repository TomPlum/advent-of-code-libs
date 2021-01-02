package io.github.tomplum.libs.input.types

import io.github.tomplum.libs.input.InputReader

/**
 * A String input wrapper returned by [InputReader]
 */
class StringInput(values: List<String>) : Input<String>(values)