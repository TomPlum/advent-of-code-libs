package io.github.tomplum.libs.input

import assertk.assertThat
import assertk.assertions.isEqualTo
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


    @Test
    fun badPath() {
        val e = assertThrows<IllegalArgumentException> {
            InputReader.read<Double>(Day(85))
        }
        val expectedMessage = "Cannot find /day85/input.txt in resources. " +
                "Did you forget to add the puzzle input?"
        assertThat(e.message).isEqualTo(expectedMessage)
    }
}