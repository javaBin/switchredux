package no.javazone.switchredux

enum class SetupValue(val defaultValue:String) {
    DBHOST("localhost"),
    DBPORT("5432"),
    DATASOURCENAME("switcharoolocal"),
    DBUSER("localdevuser"),
    DBPASSWORD("localdevuser"),
    DATABASE_TYPE("POSTGRES"),
    RUN_FROM_JAR("false"),
}
object Setup {
    fun readValue(setupValue: SetupValue):String = setupValue.defaultValue

    fun readBoolValue(setupValue: SetupValue) = (readValue(setupValue) == "true")
}