package io.github.tomplum.libs.solutions

import io.github.tomplum.libs.solutions.benchmark.data.Benchmark
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.github.tomplum.libs.solutions.benchmark.utility.BenchmarkUtility
import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkReport
import io.github.tomplum.libs.logging.AdventLogger
import java.time.Year
import kotlin.system.measureNanoTime

/**
 * A companion utility class designs to run multiple [Solution] implementations.
 * [AdventLogger.error] is used for logging to prevent lower level logs from disrupting the report.
 */
class SolutionRunner private constructor() {
    companion object {
        /**
         * Runs the given [solutions], measures their runtime and print a [BenchmarkReport]
         * to the console.
         * @param solutions The solutions to run.
         */
        fun run(year: Year, vararg solutions: Solution<*, *>) {
            val result = BenchmarkResult()

            solutions.map { solution ->
                val p1 = runPart(solution, Part.ONE)
                val p2 = runPart(solution, Part.TWO)
                val benchmark = Benchmark(solution.dayNumber(), p1.answer, p2.answer, p1.runtime, p2.runtime)
                result.add(benchmark)
            }

            BenchmarkUtility(year).log(result)
        }

        /**
         * Runs the chosen [part] for the given [solution] and measures its runtime in nanoseconds.
         * @param solution The solution.
         * @param part The part to run.
         * @return The answer returned by the solution and its runtime.
         */
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

        /**
         * Extracts the day number from the given solution class.
         * Supports only 1 or 2 digit day numbers.
         * @return The day number of the given solution.
         */
        private fun Solution<*, *>.dayNumber(): Int {
            val lastTwo = this.javaClass.simpleName.takeLast(2)
            return if (lastTwo.all { it.isDigit() }) lastTwo.toInt() else lastTwo.takeLast(1).toInt()
        }

        /**
         * Represents an answer to a puzzle.
         * @param answer The answer as given be the user
         * @param runtime The time taken for the solution to run in milliseconds
         */
        private class Answer(val answer: Any?, val runtime: Long)

        /**
         * Represents the parts of a question.
         * Each Advent of Code puzzle has two parts to it.
         */
        private enum class Part { ONE, TWO }
    }
}