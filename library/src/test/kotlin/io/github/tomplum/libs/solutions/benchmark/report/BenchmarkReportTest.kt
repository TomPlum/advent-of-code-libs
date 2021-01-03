package io.github.tomplum.libs.solutions.benchmark.report

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BenchmarkReportTest {

    /**
     * For reference;
     * - 1s  = 1,000,000,000 ns
     * - 1ms = 1,000,000 ns
     * - 1μs = 1000 ns
     *
     * - 1s = 1,000,000 μs
     * - 1ms = 1000 μs
     */
    @Nested
    inner class FormatTime {
        @Test
        fun oneSecond() {
            assertThat(TestReport().format(1_000_000_000)).isEqualTo("1s 0ms")
        }

        @Test
        fun oneSecondAndTenMilliseconds() {
            assertThat(TestReport().format(1_010_000_000)).isEqualTo("1s 10ms")
        }

        @Test
        fun lessThanOneSecond() {
            assertThat(TestReport().format(900_000_000)).isEqualTo("900ms")
        }

        @Test
        fun justOverOneMillisecond() {
            assertThat(TestReport().format(1_100_000)).isEqualTo("1ms")
        }

        @Test
        fun lessThanOneMillisecond() {
            assertThat(TestReport().format(900_000)).isEqualTo("900μs")
        }
    }

    private inner class TestReport: BenchmarkReport() {
        fun format(nanos: Long) = formatTime(nanos)
    }
}