package io.github.tomplum.libs.input.types

class IntegerInput(input: List<String>) : Input<Int>(input.map { it.toInt() })