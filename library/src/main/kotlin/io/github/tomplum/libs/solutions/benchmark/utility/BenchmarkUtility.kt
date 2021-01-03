package io.github.tomplum.libs.solutions.benchmark.utility

import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkComparison
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkCompactReport
import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkComparisonReport
import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkDefaultReport
import io.github.tomplum.libs.solutions.benchmark.report.ReportingMode
import io.github.tomplum.libs.logging.AdventLogger

class BenchmarkUtility {
    private val reader = BenchmarkReader()
    private val writer = BenchmarkWriter()

    fun log(currentRun: BenchmarkResult) {
        val previousRun = reader.read()
        if (previousRun != null) {
            val comparison = BenchmarkComparison(previousRun, currentRun)
            val mode = when(System.getProperty("report")) {
                "verbose" -> ReportingMode.VERBOSE
                "compact" -> ReportingMode.COMPACT
                else -> ReportingMode.DEFAULT
            }

            when(mode) {
                ReportingMode.VERBOSE -> AdventLogger.error(BenchmarkComparisonReport(comparison))
                ReportingMode.COMPACT -> AdventLogger.error(BenchmarkCompactReport(comparison))
                ReportingMode.DEFAULT -> AdventLogger.error(BenchmarkComparisonReport(comparison))
            }

        } else {
            AdventLogger.error(BenchmarkDefaultReport(currentRun))
        }
        writer.write(currentRun)
    }
}