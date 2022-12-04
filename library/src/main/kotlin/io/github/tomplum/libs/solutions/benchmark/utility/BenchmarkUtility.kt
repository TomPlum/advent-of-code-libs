package io.github.tomplum.libs.solutions.benchmark.utility

import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkComparison
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkCompactReport
import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkComparisonReport
import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkDefaultReport
import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.solutions.benchmark.report.ReportingMode.*
import java.time.Year

class BenchmarkUtility(private val year: Year) {
    private val reader = BenchmarkReader()
    private val writer = BenchmarkWriter()

    /**
     * Logs the [currentRun] of solutions to the console with benchmarks.
     * Respects the "report" system property should it be set.
     * @param currentRun The results of the current run of solutions
     */
    fun log(currentRun: BenchmarkResult) {
        val previousRun = reader.read()

        if (previousRun != null) {
            val comparison = BenchmarkComparison(previousRun, currentRun)

            val mode = when(System.getProperty("report")) {
                "verbose" -> VERBOSE
                "compact" -> COMPACT
                else -> DEFAULT
            }

            val reportContents = when(mode) {
                VERBOSE -> BenchmarkComparisonReport(comparison)
                COMPACT -> BenchmarkCompactReport(comparison)
                DEFAULT -> BenchmarkComparisonReport(comparison)
            }.getReportContents()

            val report = StringBuilder("- Advent of Code ${year.value} Solution Report -\n\n")
            report.append(reportContents)
            AdventLogger.error(report)

        } else {
            AdventLogger.error(BenchmarkDefaultReport(currentRun))
        }

        writer.write(currentRun)
    }
}