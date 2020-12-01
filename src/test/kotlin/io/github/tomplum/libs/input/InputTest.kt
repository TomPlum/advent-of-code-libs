package io.github.tomplum.libs.input

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.libs.input.types.IntegerInput
import org.junit.jupiter.api.Test

class InputTest {
    @Test
    fun asSingleString() {
        assertThat(IntegerInput(listOf("1", "4", "6")).asSingleString()).isEqualTo("1\n4\n6")
    }
}