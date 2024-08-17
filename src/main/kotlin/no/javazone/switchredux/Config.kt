package no.javazone.switchredux

import java.io.File
import java.util.concurrent.*

enum class ConfigVariable(val defaultValue:String) {
    TIME_SOURCE("REAL")
    ;

    fun readValue():String = Config.readValue(this)

}

object Config {
    private val configValues:MutableMap<ConfigVariable,String> = ConcurrentHashMap()


    fun readValue(configVariable: ConfigVariable):String = configValues[configVariable]?:configVariable.defaultValue

    fun loadValues(args: Array<String>) {
        if (args.isEmpty()) {
            return
        }
        for (line in File(args[0]).readLines()) {
            if (line.startsWith("#")) {
                continue
            }
            val parts = line.split("=")
            if (parts.size == 2) {
                configValues[ConfigVariable.valueOf(parts[0])] = parts[1]
            }
        }
    }

}