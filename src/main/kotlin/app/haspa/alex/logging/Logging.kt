package app.haspa.alex.logging

import org.apache.logging.log4j.CloseableThreadContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.logging.log4j.ThreadContext
import org.apache.logging.log4j.core.config.Configurator

data class LoggingConfig(
    val service: String,
    val environment: String
)

var config: LoggingConfig? = null
var log: Logger? = null

fun initLogging(loggingConfig: LoggingConfig) {
    config = loggingConfig
    Configurator.initialize(null, "log4j2-local.yaml")
    log = LoggerFactory.getLogger("kotlin-logging")
}

fun logging(tag: String, fn: (Logger) -> Unit) {
    logging(arrayOf(tag), fn)
}

fun logging(tags: Array<String>, fn: (Logger) -> Unit) {
    CloseableThreadContext.pushAll(tags.toMutableList()).use {
        logger()?.let(fn)
    }
}

fun logger(): Logger? {
    if (config == null) {
        val e = Error("kotlin-logging called without having been initialized")
        println(e.message!!)
        e.printStackTrace()
        return null
    }
    ThreadContext.put("service", config!!.service)
    ThreadContext.put("environment", config!!.environment)
    return log!!
}
