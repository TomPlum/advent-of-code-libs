package io.github.tomplum.libs.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RangeExtensionsTest {
    @Nested
    inner class IntRangeMidpoint {
        @Test
        fun bothEven() {
            assertThat(IntRange(2, 6).midpoint()).isEqualTo(4)
        }

        @Test
        fun zeroStart() {
            assertThat(IntRange(0, 127).midpoint()).isEqualTo(63)
        }
    }
}
