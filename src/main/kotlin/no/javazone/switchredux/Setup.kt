package no.javazone.switchredux

import no.javazone.switchredux.Setup.readValue
import java.io.File
import java.util.concurrent.ConcurrentHashMap

enum class SetupValue(val defaultValue:String) {
    DBHOST("localhost"),
    DBPORT("5432"),
    DATASOURCENAME("switcharoolocal"),
    DBUSER("localdevuser"),
    DBPASSWORD("localdevuser"),
    DATABASE_TYPE("POSTGRES"),
    RUN_FROM_JAR("false"),
    TIME_SOURCE("REAL"),
    USE_DB("false"),
    MINUTES_LIMIT_PROGRAM("10"),
    SERVER_PORT("8080"),
    ADMIN_PASSWORD("bingo"),
    ;

    fun readValue():String = readValue(this)

    fun readBoolValue():Boolean = (readValue(this) == "true")

    fun readLongValue():Long = readValue(this).toLong()
}
object Setup {
    private val valueMap:MutableMap<SetupValue,String> = ConcurrentHashMap()

    fun loadValues(args: Array<String>) {
        if (args.isEmpty()) {
            return
        }
        for (line in File(args[0]).readLines()) {
            if (line.startsWith("#")) {
                continue
            }
            val pos = line.indexOf("=")
            if (pos == -1) {
                continue
            }
            valueMap[SetupValue.valueOf(line.substring(0,pos))] = line.substring(pos+1)

        }
    }

    fun readValue(setupValue: SetupValue):String = valueMap[setupValue]?:setupValue.defaultValue


}