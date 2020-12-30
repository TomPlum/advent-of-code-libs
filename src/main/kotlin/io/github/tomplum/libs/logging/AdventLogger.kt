package io.github.tomplum.libs.logging

import org.slf4j.LoggerFactory.getLogger

/**
 * Companion logger for usage in Advent of Code Implementation.
 * - Configuration for the Log4J2 Implementation is defined in the log4j2.properties file in the resources.
 * - The logging functions in the class are ordered in ascending granularity.
 * @see org.slf4j.Logger
 */
class AdventLogger private constructor() : Logger {
    companion object {
        @JvmStatic
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)

        /**
         * The ERROR level designates error events that might still allow the application to continue running.
         */
        fun error(message: String) = logger.error(message)
        fun <E> error(obj: E) = logger.error("{}", obj)

        /**
         * The WARN level designates potentially harmful situations.
         */
        fun warn(message: String) = logger.warn(message)
        fun <E> warn(obj: E) = logger.warn("{}", obj)

        /**
         * The INFO level designates information messages that highlight the progress of the application
         * at a coarse-grained level.
         */
        fun info(message: String) = logger.info(message)
        fun <E> info(obj: E) = logger.info("{}", obj)

        /**
         * The DEBUG level designates fine-grained informational events that are most useful to debug the application.
         */
        fun debug(message: String) = logger.debug(message)
        fun <E> debug(obj: E) = logger.debug("{}", obj)

        /**
         * The TRACE level designates finer-grained informational events than the [debug] level.
         */
        fun trace(message: String) = logger.trace(message)
        fun <E> trace(obj: E) = logger.trace("{}", obj)
    }
}