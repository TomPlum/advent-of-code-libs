package io.github.tomplum.libs.solutions

import io.github.tomplum.libs.solutions.benchmark.data.Benchmark
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.github.tomplum.libs.solutions.benchmark.utility.BenchmarkUtility
import io.github.tomplum.libs.logging.AdventLogger
import kotlin.system.measureNanoTime

/**
 * A companion utility class designs to run multiple [Solution] implementations.
 * [AdventLogger.error] is used for logging to prevent lower level logs from disrupting the report.
 */
class SolutionRunner private constructor() {
    companion object {
        fun run(vararg solutions: Solution<*, *>) {
            val result = BenchmarkResult()

            solutions.map { solution ->
                val p1 = runPart(solution, Part.ONE)
                val p2 = runPart(solution, Part.TWO)
                val benchmark = Benchmark(solution.dayNumber(), p1.answer, p2.answer, p1.runtime, p2.runtime)
                result.add(benchmark)
            }

            BenchmarkUtility().log(result)
        }

        private fun runPart(solution: Solution<*, *>, part: Part): Answer {
            var answer: Any?
            val runtime = measureNanoTime {
                answer = when(part) {
                    Part.ONE -> solution.part1()
                    Part.TWO -> solution.part2()
                }
            }
            return Answer(answer, runtime)
        }

        private fun Solution<*, *>.dayNumber(): Int {
            val lastTwo = this.javaClass.simpleName.takeLast(2)
            return if (lastTwo.all { it.isDigit() }) lastTwo.toInt() else lastTwo.takeLast(1).toInt()
        }

        private class Answer(val answer: Any?, val runtime: Long)

        private enum class Part { ONE, TWO }
    }
}