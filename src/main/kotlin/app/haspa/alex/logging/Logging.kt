package app.haspa.alex.logging

import org.apache.logging.log4j.*
import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.message.ObjectMessage

var log: Logger = LogManager.getLogger("kotlin-logging")
object Logging : Logger by log {

    data class LoggingConfig(
        val service: String,
        val environment: String
    )

    var config: LoggingConfig? = null

    fun init(loggingConfig: LoggingConfig) {
        config = loggingConfig
        Configurator.initialize(null, "log4j2-local.yaml")
        log = LogManager.getLogger("kotlin-logging")
    }

    fun info(msg: String, marker: String? = null, data: Map<String, Any>? = null) {
        log(Level.INFO, msg, marker, data)
    }

    fun error(msg: String, marker: String? = null, data: Map<String, Any>? = null) {
        log(Level.ERROR, msg, marker, data)
    }

    fun warn(msg: String, marker: String? = null, data: Map<String, Any>? = null) {
        log(Level.WARN, msg, marker, data)
    }

    fun debug(msg: String, marker: String? = null, data: Map<String, Any>? = null) {
        log(Level.DEBUG, msg, marker, data)
    }

    fun log(level: Level, msg: String, marker: String?, data: Map<String, Any>? = null) {
        logger()?.let {
            if (data != null) {
                val msgObj = data.toMutableMap()
                msgObj["message"] = msg
                val dataMsg = ObjectMessage(msgObj)
                it.log(level, if (marker != null) marker(marker) else null, dataMsg)
            } else {
                it.log(level, if (marker != null) marker(marker) else null, msg)
            }
        }
    }

    fun prepareMarker(name: String, parents: Array<String> = emptyArray()) {
        val marker = MarkerManager.getMarker(name)
        val parentMarkers = parents.map { MarkerManager.getMarker(it) }
        if (parentMarkers.isNotEmpty()) {
            marker.setParents(*parentMarkers.toTypedArray())
        }
    }

    fun marker(name: String): Marker {
        return MarkerManager.getMarker(name)
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
        return log
    }

}
