package io.github.tomplum.libs.input

import io.github.tomplum.libs.input.types.Input
import io.github.tomplum.libs.input.types.IntegerInput
import io.github.tomplum.libs.input.types.LongInput
import io.github.tomplum.libs.input.types.StringInput
import java.io.File

/**
 * A companion utility class for de-serialising puzzle input text files.
 * This class is open for extension so a test implementation can be created.
 */
open class InputReader private constructor() {
    companion object {
        /**
         * Reads the puzzle input for a single day.
         *
         * Supports [Int], [Long] and [String].
         *
         * @param T the desired return type for the lines in the input file.
         * @param day the day whose puzzle input you wish to read.
         * @throws UnsupportedOperationException if [T] is an unsupported data type.
         * @return The [Input] for the given [day] in the provided data type [T].
         */
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T : Any> read(day: Day): Input<T> {
            val lines = File(InputReader::class.java.getResource("/day${day.value}/input.txt").path).readLines()

            return when (val cls = T::class.java) {
                String::class.java -> StringInput(lines) as Input<T>
                Int::class.javaObjectType -> IntegerInput(lines) as Input<T>
                Long::class.javaObjectType -> LongInput(lines) as Input<T>
                else -> throw UnsupportedOperationException("Input Reader does not support type: ${cls.simpleName}")
            }
        }
    }
}
