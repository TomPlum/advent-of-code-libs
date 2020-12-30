package io.github.tomplum.libs.logging

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdventLoggerTest {
    @Nested
    inner class Error {
        @Test
        fun error() {
            AdventLogger.error("ERROR LOG!")
        }

        @Test
        fun errorObject() {
            AdventLogger.error(ExampleObject("ERROR"))
        }

        @Test
        fun errorTemplate() {
            AdventLogger.error("This is an {} test", "ERROR")
        }
    }

    @Nested
    inner class Warn {
        @Test
        fun warn() {
            AdventLogger.warn("WARN LOG!")
        }

        @Test
        fun warnObject() {
            AdventLogger.warn(ExampleObject("WARN"))
        }

        @Test
        fun warnTemplate() {
            AdventLogger.warn("This is an {} test", "WARN")
        }
    }

    @Nested
    inner class Info {
        @Test
        fun info() {
            AdventLogger.info("INFO LOG!")
        }

        @Test
        fun infoObject() {
            AdventLogger.info(ExampleObject("INFO"))
        }

        @Test
        fun infoTemplate() {
            AdventLogger.info("This is an {} test", "INFO")
        }
    }

    @Nested
    inner class Debug {
        @Test
        fun debug() {
            AdventLogger.debug("DEBUG LOG!")
        }

        @Test
        fun debugObject() {
            AdventLogger.debug(ExampleObject("DEBUG"))
        }

        @Test
        fun debugTemplate() {
            AdventLogger.debug("This is an {} test", "DEBUG")
        }
    }

    @Nested
    inner class Trace {
        @Test
        fun trace() {
            AdventLogger.trace("TRACE LOG!")
        }

        @Test
        fun traceObject() {
            AdventLogger.trace(ExampleObject("TRACE"))
        }

        @Test
        fun traceTemplate() {
            AdventLogger.trace("This is an {} test", "TRACE")
        }
    }

    private inner class ExampleObject(val level: String) {
        override fun toString() = "I am an overridden toString() function in an ExampleObject. I was logged at $level"
    }

}