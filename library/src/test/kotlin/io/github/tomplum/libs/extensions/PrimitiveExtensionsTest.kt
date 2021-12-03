package io.github.tomplum.libs.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PrimitiveExtensionsTest {
    @Nested
    inner class IntToBinary {
        @Test
        fun decimalTo36bit() {
            val binary = 11.toBinary(36)
            val expected = IntArray(36)
            expected[35] = 1
            expected[34] = 1
            expected[32] = 1
            assertThat(binary).isEqualTo(expected)
        }
    }
}
