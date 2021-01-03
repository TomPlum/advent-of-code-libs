package io.github.tomplum.libs.solutions.benchmark.data

import com.fasterxml.jackson.annotation.JsonIgnore

class BenchmarkResult {
    val results = mutableListOf<Benchmark<*>>()

    fun add(benchmark: Benchmark<*>) = results.add(benchmark)

    fun get(day: Int): Benchmark<*> = results.find { it.day == day } ?: throw IllegalArgumentException("No Benchmark Result For Day $day")

    @JsonIgnore
    fun getTotalTime(): Long = results.map { it.runtime1 + it.runtime2 }.sum()

    @JsonIgnore
    fun getAvgTime(): Long = results.map { it.runtime1 + it.runtime2 }.average().toLong()
}