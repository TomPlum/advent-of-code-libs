package io.github.tomplum.libs.solutions.benchmark.utility

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.github.tomplum.libs.logging.AdventLogger
import java.io.FileNotFoundException
import java.io.FileReader

/**
 * Reads an existing [BenchmarkResult] from it's serialised XML file.
 */
class BenchmarkReader {
    /**
     * Looks for an existing 'benchmark.xml' file and maps it to a [BenchmarkResult].
     * @return The previous benchmark result or null if it doesn't exist.
     */
    fun read(fileLocation: String = "benchmark.xml"): BenchmarkResult? {
        val reader = try {
            FileReader(fileLocation)
        } catch (e: FileNotFoundException) {
            AdventLogger.error("Cannot find previous run. Deltas will not be reported.\n")
            return null
        }
        return XmlMapper().readValue(reader, BenchmarkResult::class.java)
    }
}