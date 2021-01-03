package io.github.tomplum.libs.solutions.benchmark.utility

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.github.tomplum.libs.logging.AdventLogger
import java.io.FileNotFoundException
import java.io.FileReader

class BenchmarkReader {
    fun read(): BenchmarkResult? {
        val reader = try {
            FileReader("benchmark.xml")
        } catch (e: FileNotFoundException) {
            AdventLogger.error("Cannot find previous run. Deltas will not be reported.\n")
            return null
        }
        return XmlMapper().readValue(reader, BenchmarkResult::class.java)
    }
}