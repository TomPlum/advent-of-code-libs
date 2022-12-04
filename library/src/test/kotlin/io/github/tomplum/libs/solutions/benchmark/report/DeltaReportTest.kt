package io.github.tomplum.libs.solutions.benchmark.report

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DeltaReportTest {
    /**
     * For reference, times are;
     * - 1s  = 1,000,000,000 ns
     * - 1ms = 1,000,000 ns
     * - 1μs = 1000 ns
     *
     * - 1s = 1,000,000 μs
     * - 1ms = 1000 μs
     *
     * And colour codes;
     * - 32 is green
     * - 31 is red
     * - 33 is yellow
     */
    @Nested
    inner class FormatExecutionTime {
        private val testReport = TestReport()

        @Test
        fun positiveSecondDelta_shouldPrintRed() {
            val executionTime = testReport.executionTime(runtime = 100_000_000, delta = 1000_000_000)
            assertThat(executionTime).isEqualTo("100ms ([31m+1s 0ms[0m)\n")
        }

        @Test
        fun positiveMillisecondDelta_shouldPrintRed() {
            val executionTime = testReport.executionTime(runtime = 100_000_000, delta = 15_000_000)
            assertThat(executionTime).isEqualTo("100ms ([31m+15ms[0m)\n")
        }

        @Test
        fun positiveMicroSecondDelta_shouldPrintYellow() {
            val executionTime = testReport.executionTime(runtime = 600_000, delta = 250_000)
            assertThat(executionTime).isEqualTo("600μs ([33m+250μs[0m)\n")
        }

        @Test
        fun negativeMicroSecondDelta_shouldPrintGreen() {
            val executionTime = testReport.executionTime(runtime = 600_000, delta = -150_000)
            assertThat(executionTime).isEqualTo("600μs ([32m-150μs[0m)\n")
        }

        @Test
        fun negativeMillisecondDelta_shouldPrintGreen() {
            val executionTime = testReport.executionTime(runtime = 600_000, delta = -236_000_000)
            assertThat(executionTime).isEqualTo("600μs ([32m-236ms[0m)\n")
        }

        @Test
        fun negativeSecondDelta_shouldPrintGreen() {
            val executionTime = testReport.executionTime(runtime = 600_000, delta = -1000_000_000)
            assertThat(executionTime).isEqualTo("600μs ([32m-1s 0ms[0m)\n")
        }

        @Test
        fun zeroTimeDelta_shouldPrintColourless() {
            val executionTime = testReport.executionTime(runtime = 3000_000_000, delta = 0)
            assertThat(executionTime).isEqualTo("3s 0ms (±0ms)\n")
        }
    }

    private inner class TestReport: DeltaReport() {
        fun executionTime(runtime: Long, delta: Long): String = formatExecutionTime(runtime, delta)

        override fun getReportContents(): String {
            return "Report Contents"
        }
    }
}