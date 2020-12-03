package io.github.tomplum.libs.logging

import assertk.assertThat
import assertk.assertions.isNotNull
import org.junit.jupiter.api.Test

class AdventLoggerTest {
     @Test
     fun debug() {
         AdventLogger.debug("DEBUG LOG!")
     }

    @Test
    fun debugObject() {
        AdventLogger.debug(ExampleObject("DEBUG"))
    }

    @Test
    fun info() {
        AdventLogger.info("INFO LOG!")
    }

    @Test
    fun infoObject() {
        AdventLogger.info(ExampleObject("INFO"))
    }

    @Test
    fun warn() {
        AdventLogger.warn("WARN LOG!")
    }

    @Test
    fun warnObject() {
        AdventLogger.warn(ExampleObject("WARN"))
    }

    @Test
    fun error() {
        AdventLogger.error("ERROR LOG!")
    }

    @Test
    fun errorObject() {
        AdventLogger.error(ExampleObject("ERROR"))
    }

    @Test
    fun trace() {
        AdventLogger.trace("TRACE LOG!")
    }

    @Test
    fun traceObject() {
        AdventLogger.trace(ExampleObject("TRACE"))
    }

    private inner class ExampleObject(val level: String) {
        override fun toString() = "I am an overridden toString() function in an ExampleObject. I was logged at $level"
    }

}