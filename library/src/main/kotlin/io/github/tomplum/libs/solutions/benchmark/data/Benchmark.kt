package io.github.tomplum.libs.solutions.benchmark.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class Benchmark<T> @JsonCreator constructor(
    @JsonProperty("day") val day: Int,
    @JsonProperty("answer1") val answer1: T,
    @JsonProperty("answer2") val answer2: T,
    @JsonProperty("runtimeP1") val runtime1: Long,
    @JsonProperty("runtimeP2") val runtime2: Long
)