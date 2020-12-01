package io.github.tomplum.libs.input

import io.github.tomplum.libs.input.types.Input
import io.github.tomplum.libs.input.types.IntegerInput
import io.github.tomplum.libs.input.types.StringInput
import java.io.File

open class InputReader {
    companion object {
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T : Any> read(day: Day): Input<T> {
            val lines = File(InputReader::class.java.getResource("/day${day.value}/input.txt").path).readLines()

            return when (val cls = T::class.java) {
                String::class.java -> StringInput(lines) as Input<T>
                Integer::class.java -> IntegerInput(lines) as Input<T>
                else -> throw UnsupportedOperationException("Input Reader does not support type: ${cls.simpleName}")
            }
        }
    }
}
