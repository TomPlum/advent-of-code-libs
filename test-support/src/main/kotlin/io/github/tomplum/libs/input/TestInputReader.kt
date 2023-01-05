package io.github.tomplum.libs.input

import io.github.tomplum.libs.input.types.Input
import io.github.tomplum.libs.input.types.IntegerInput
import io.github.tomplum.libs.input.types.LongInput
import io.github.tomplum.libs.input.types.StringInput
import java.io.File

class TestInputReader private constructor(){
    companion object {
        /**
         * Reads a file from the "input" package from the
         * resources directory of the test source-set.
         * @param path The path relative to the input directory
         * @return The contents of the file
         */
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T : Any> read(path: String): Input<T> {
            val inputPath = "/input/$path"
            val uri = InputReader::class.java.getResource(inputPath)
                ?: throw IllegalArgumentException("Cannot find test input files in $inputPath. Did you forget to add the example input?")

            val lines = File(uri.path).readLines()

            return when (val cls = T::class.java) {
                String::class.java -> StringInput(lines) as Input<T>
                Int::class.javaObjectType -> IntegerInput(lines) as Input<T>
                Long::class.javaObjectType -> LongInput(lines) as Input<T>
                else -> throw UnsupportedOperationException("Input Reader does not support type: ${cls.simpleName}")
            }
        }
    }
}