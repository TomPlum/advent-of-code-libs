package io.github.tomplum.libs.solutions.benchmark.report

import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.github.tomplum.libs.solutions.benchmark.utility.BenchmarkReader

/**
 * The default [BenchmarkReport] that is used to format the [BenchmarkResult] when
 * the [BenchmarkReader] cannot find an existing XML result from a previous run.
 * Simply reports the solutions from the current [result] as runtime deltas are not possible.
 * @param result The benchmark result to format.
 */
class BenchmarkDefaultReport(private val result: BenchmarkResult) : BenchmarkReport() {

    override fun toString(): String {
        val s = StringBuilder("- Advent of Code 2020 Solution Report -\n\n")
        val solutions =  result.results.joinToString("\n") { result ->
            val builder = StringBuilder("[Day ${result.day}]\n")

            builder.append("Part 1: ${result.answer1}\n")
            builder.append(formatExecutionTime(result.runtime1))
            builder.append("\n")
            builder.append("Part 2: ${result.answer2}\n")
            builder.append(formatExecutionTime(result.runtime2))

            builder.toString()
        }
        s.append(solutions).append("\n")

        s.append("Average ${formatExecutionTime(result.getAvgTime())}")
        s.append("Total ${formatExecutionTime(result.getTotalTime())}")
        return s.toString()
    }

    private fun formatExecutionTime(runtime: Long): String {
        return "Execution Time: ${formatTime(runtime)}\n"
    }
}