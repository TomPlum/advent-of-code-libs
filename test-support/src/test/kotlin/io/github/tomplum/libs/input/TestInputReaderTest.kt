package io.github.tomplum.libs.input

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.libs.input.types.StringInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TestInputReaderTest {
    @Test
    fun asString() {
        val value = TestInputReader.read<String>("example-input.txt").value
        assertThat(value).isEqualTo(listOf("1", "2", "3", "4", "5"))
    }

    @Test
    fun asInt() {
        val value = TestInputReader.read<Int>("example-input.txt").value
        assertThat(value).isEqualTo(listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun asInteger() {
        val value = TestInputReader.read<Integer>("example-input.txt").value
        assertThat(value).isEqualTo(listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun asLong() {
        val value = TestInputReader.read<Long>("example-input.txt").value
        assertThat(value).isEqualTo(listOf<Long>(1, 2, 3, 4, 5))
    }

    @Test
    fun asSingleString() {
        val input: StringInput = TestInputReader.read<String>("example-input.txt") as StringInput
        assertThat(input.asSingleString()).isEqualTo("1\n2\n3\n4\n5")
    }

    @Test
    fun asInvalidType() {
        val e = org.junit.jupiter.api.assertThrows<UnsupportedOperationException> {
            TestInputReader.read<Double>("example-input.txt")
        }
        assertThat(e.message).isEqualTo("Input Reader does not support type: Double")
    }
}