package io.github.tomplum.libs.solutions.benchmark.report

import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkComparison

/**
 * The default verbose variant of [DeltaReport].
 * @see BenchmarkCompactReport for a more succinct variant.
 * @param comparison The benchmark result to format.
 */
class BenchmarkComparisonReport(private val comparison: BenchmarkComparison) : DeltaReport() {

    override fun toString(): String {
        val s = StringBuilder("- Advent of Code 2020 Solution Report -\n\n")
        val solutions =  comparison.getDeltas().joinToString("\n") { delta ->
            val builder = StringBuilder("[Day ${delta.day}]\n")

            val lastRun = comparison.lastRun.get(delta.day)

            builder.append("Part 1: ${lastRun.answer1}\n")
            builder.append("Execution Time: ${formatExecutionTime(lastRun.runtime1, delta.p1)}")
            builder.append("\n")
            builder.append("Part 2: ${lastRun.answer2}\n")
            builder.append("Execution Time: ${formatExecutionTime(lastRun.runtime2, delta.p2)}")

            builder.toString()
        }
        s.append(solutions).append("\n")

        val lastRun = comparison.lastRun
        s.append("Average Execution Time: ${formatExecutionTime(lastRun.getAvgTime(), comparison.getAvgDelta())}")
        s.append("Total Execution Time: ${formatExecutionTime(lastRun.getTotalTime(), comparison.getTotalDelta())}")
        return s.toString()
    }
}