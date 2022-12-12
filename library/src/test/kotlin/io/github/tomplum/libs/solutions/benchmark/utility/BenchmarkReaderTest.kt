package io.github.tomplum.libs.solutions.benchmark.utility

import assertk.assertThat
import assertk.assertions.containsExactly
import io.github.tomplum.libs.solutions.benchmark.data.Benchmark
import org.junit.jupiter.api.Test

class BenchmarkReaderTest {

    private val benchmarkReader = BenchmarkReader()

    @Test
    fun shouldReadTheBenchmarkXmlFileFromResources_thenReturnDeSerialisedResult() {
        val fileLocation = "${System.getProperty("user.dir")}/src/test/resources/benchmark.xml"
        val result = benchmarkReader.read(fileLocation)
        assertThat(result!!.results).containsExactly(
            Benchmark(1, 67633, 199628, 35252946, 2153846),
            Benchmark(2, 11449, 13187, 2910280, 2595123),
            Benchmark(3, 7581, 2525, 4133315, 6328752),
            Benchmark(4, 413, 806, 11498876, 10974261)
        )
    }
}