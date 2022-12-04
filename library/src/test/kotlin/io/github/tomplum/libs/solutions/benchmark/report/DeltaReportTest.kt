package io.github.tomplum.libs.solutions.benchmark.report

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DeltaReportTest {
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
    inner class FormatExecutionTime {
        @Test
        fun millisecondDelta() {
            assertThat(TestReport().executionTime(100_000_000, 15_000_000)).isEqualTo("100ms ([31m+15ms[0m)\n")
        }

        @Test
        fun microSecondDelta() {
            assertThat(TestReport().executionTime(600_000, 250_000)).isEqualTo("600μs ([31m+250μs[0m)\n")
        }
    }

    private inner class TestReport: DeltaReport() {
        fun executionTime(runtime: Long, delta: Long): String = formatExecutionTime(runtime, delta)

        override fun getReportContents(): String {
            return "Report Contents"
        }
    }
}