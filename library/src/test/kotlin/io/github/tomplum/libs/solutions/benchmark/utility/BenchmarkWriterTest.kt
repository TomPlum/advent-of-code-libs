package io.github.tomplum.libs.solutions.benchmark.utility

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.github.tomplum.libs.solutions.benchmark.data.Benchmark
import io.github.tomplum.libs.solutions.benchmark.data.BenchmarkResult
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File

@ExtendWith(MockKExtension::class)
class BenchmarkWriterTest {

    @MockK(relaxUnitFun = true)
    private lateinit var mapper: XmlMapper

    private lateinit var writer: BenchmarkWriter

    @BeforeEach
    fun setUp() {
        writer = BenchmarkWriter(mapper)
    }

    @Test
    fun shouldWriteResultToFile() {
        every { mapper.enable(SerializationFeature.INDENT_OUTPUT) } returns mapper
        val result = BenchmarkResult()
        result.add(Benchmark(1, 450, 12467, 500000L, 12392423L))

        writer.write(result)

        verify { mapper.writeValue(File("benchmark.xml"), result) }
    }
}