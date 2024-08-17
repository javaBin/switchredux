package no.javazone.switchredux

import org.jsonbuddy.JsonObject
import org.jsonbuddy.pojo.JsonGenerator

enum class CommandStatus {
    OK,ERROR
}
abstract class CommandResult {

    open val commandStatus: CommandStatus = CommandStatus.OK
    open fun toJson():JsonObject {
        val res:JsonObject = JsonGenerator.generate(this) as JsonObject
        if (!res.containsKey("status")) {
            res.put("status",commandStatus.name)
        }
        res.remove("commandStatus")
        return res
    }

    open val httpStatusCode: HttpStatusCode = HttpStatusCode.OK;
}

class CommandError(override val httpStatusCode: HttpStatusCode, private val errormessage:String): CommandResult() {
    override fun toJson(): JsonObject {
        return JsonObject().put("errormessage",errormessage)
    }

    override val commandStatus: CommandStatus = CommandStatus.ERROR

}

class EmptyResult(override val httpStatusCode: HttpStatusCode = HttpStatusCode.OK): CommandResult() {

}