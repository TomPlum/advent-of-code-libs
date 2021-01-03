package io.github.tomplum.libs.solutions.benchmark.report

import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkComparison

/**
 * A more succinct variant of [DeltaReport].
 * Still reports the same information as [BenchmarkComparisonReport] but omits
 * all the unnecessary line breaks.
 * @param comparison The benchmark result to format.
 */
class BenchmarkCompactReport(private val comparison: BenchmarkComparison) : DeltaReport() {

    override fun toString(): String {
        val s = StringBuilder("- Advent of Code 2020 Solution Report -\n\n")
        val solutions =  comparison.getDeltas().joinToString("\n") { delta ->
            val builder = StringBuilder("[Day ${delta.day}]\n")

            val lastRun = comparison.lastRun.get(delta.day)

            builder.append("P1: ${lastRun.answer1} -> ${formatExecutionTime(lastRun.runtime1, delta.p1)}")
            builder.append("P2: ${lastRun.answer2} -> ${formatExecutionTime(lastRun.runtime2, delta.p2)}")

            builder.toString()
        }
        s.append(solutions).append("\n")

        val lastRun = comparison.lastRun
        s.append("Average Time: ${formatExecutionTime(lastRun.getAvgTime(), comparison.getAvgDelta())}")
        s.append("Total Time: ${formatExecutionTime(lastRun.getTotalTime(), comparison.getTotalDelta())}")
        return s.toString()
    }

}