package io.github.tomplum.libs.input

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.input.types.StringInput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InputReaderTest {

    @Test
    fun asString() {
        assertThat(InputReader.read<String>(Day(100)).value).isEqualTo(listOf("1", "2", "3", "4", "5"))
    }

    @Test
    fun asInt() {
        assertThat(InputReader.read<Int>(Day(100)).value).isEqualTo(listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun asInteger() {
        assertThat(InputReader.read<Integer>(Day(100)).value).isEqualTo(listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun asLong() {
        assertThat(InputReader.read<Long>(Day(100)).value).isEqualTo(listOf<Long>(1, 2, 3, 4, 5))
    }

    @Test
    fun asSingleString() {
        val input: StringInput = InputReader.read<String>(Day(100)) as StringInput
        assertThat(input.asSingleString()).isEqualTo("1\n2\n3\n4\n5")
    }

    @Test
    fun asInvalidType() {
        val e = assertThrows<UnsupportedOperationException> { InputReader.read<Double>(Day(100)) }
        assertThat(e.message).isEqualTo("Input Reader does not support type: Double")
    }
}