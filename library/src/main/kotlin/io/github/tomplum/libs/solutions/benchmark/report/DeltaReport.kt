package io.github.tomplum.libs.solutions.benchmark.report

import io.github.tomplum.libs.solutions.benchmark.report.BenchmarkReport
import io.github.tomplum.libs.solutions.benchmark.report.Colour
import io.github.tomplum.libs.solutions.benchmark.report.coloured

abstract class DeltaReport : BenchmarkReport() {
    protected fun formatExecutionTime(runtime: Long, delta: Long): String {
        return "${formatTime(runtime)} (${formatDelta(delta)})\n"
    }

    private fun formatDelta(time: Long): String = when {
        time > 0 -> "+${formatTime(time)}".coloured(Colour.RED)
        time < 0 -> formatTime(time).coloured(Colour.GREEN)
        else -> "±0ms"
    }
}