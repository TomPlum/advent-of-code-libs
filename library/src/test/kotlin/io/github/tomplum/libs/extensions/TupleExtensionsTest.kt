package io.github.tomplum.libs.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TupleExtensionsTest {
    @Nested
    inner class PairSum {
        @Test
        fun twoPositiveIntegers() {
            assertThat(Pair(10, 5).sum()).isEqualTo(15)
        }

        @Test
        fun twoNegativeIntegers() {
            assertThat(Pair(-4, -4).sum()).isEqualTo(-8)
        }

        @Test
        fun differentPolarity() {
            assertThat(Pair(15, -2).sum()).isEqualTo(13)
        }
    }

    @Nested
    inner class PairProduct {
        @Test
        fun twoPositiveIntegers() {
            assertThat(Pair(10, 5).product()).isEqualTo(50)
        }

        @Test
        fun twoNegativeIntegers() {
            assertThat(Pair(-4, -4).product()).isEqualTo(16)
        }

        @Test
        fun differentPolarity() {
            assertThat(Pair(15, -2).product()).isEqualTo(-30)
        }
    }

    @Nested
    inner class TripleSum {
        @Test
        fun threePositiveIntegers() {
            assertThat(Triple(10, 5, 2).sum()).isEqualTo(17)
        }

        @Test
        fun threeNegativeIntegers() {
            assertThat(Triple(-4, -4, -4).sum()).isEqualTo(-12)
        }

        @Test
        fun differentPolarity() {
            assertThat(Triple(15, -2, 24).sum()).isEqualTo(37)
        }
    }

    @Nested
    inner class TripleProduct {
        @Test
        fun threePositiveIntegers() {
            assertThat(Triple(10, 5, 2).product()).isEqualTo(100)
        }

        @Test
        fun threeNegativeIntegers() {
            assertThat(Triple(-4, -4, -4).product()).isEqualTo(-64)
        }

        @Test
        fun differentPolarity() {
            assertThat(Triple(15, -2, 24).product()).isEqualTo(-720)
        }
    }
}
