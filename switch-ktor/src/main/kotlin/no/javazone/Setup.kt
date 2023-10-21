package no.javazone

enum class SetupValue(val defaultValue:String) {
    DBHOST("localhost"),
    DBPORT("5432"),
    DATASOURCENAME("switcharoolocal"),
    DBUSER("localdevuser"),
    DBPASSWORD("localdevuser"),
    DATABASE_TYPE("POSTGRES"),
}
object Setup {
    fun readValue(setupValue: SetupValue):String = setupValue.defaultValue
}