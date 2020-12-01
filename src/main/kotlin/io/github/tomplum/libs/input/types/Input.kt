package io.github.tomplum.libs.input.types

abstract class Input<T>(val value: List<T>) {
    fun asSingleString() = value.joinToString(separator = "\n")
}