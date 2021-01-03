package io.github.tomplum.libs.solutions.benchmark.report

import io.github.tomplum.libs.solutions.benchmark.utility.BenchmarkUtility

/**
 * Denotes the type of benchmark report to be produced.
 * @see BenchmarkUtility
 */
enum class ReportingMode {
    COMPACT, VERBOSE, DEFAULT
}