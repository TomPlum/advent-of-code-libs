package io.github.tomplum.libs.solutions.benchmark.utility

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import java.io.File

/**
 * Writes a [BenchmarkResult] to an XML file.
 */
class BenchmarkWriter(private val mapper: XmlMapper = XmlMapper()) {
    /**
     * Writes the given [result] to a 'benchmark.xml' file.
     * @param result The result to serialise.
     */
    fun write(result: BenchmarkResult) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        mapper.writeValue(File("benchmark.xml"), result)
    }
}