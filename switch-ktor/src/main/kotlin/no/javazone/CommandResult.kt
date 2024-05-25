package no.javazone

import io.ktor.http.*
import org.jsonbuddy.JsonObject
import org.jsonbuddy.pojo.JsonGenerator

enum class CommandStatus {
    OK,ERROR
}
abstract class CommandResult {

    abstract val commandStatus:CommandStatus
    open fun toJson():JsonObject {
        val res:JsonObject = JsonGenerator.generate(this) as JsonObject
        if (!res.containsKey("status")) {
            res.put("status",commandStatus.name)
        }
        res.remove("commandStatus")
        return res
    }

    abstract val httpStatusCode:HttpStatusCode;
}

class CommandError(override val httpStatusCode: HttpStatusCode,private val errormessage:String):CommandResult() {
    override fun toJson(): JsonObject {
        return JsonObject().put("errormessage",errormessage)
    }

    override val commandStatus: CommandStatus = CommandStatus.ERROR

}

class EmptyResult(override val httpStatusCode: HttpStatusCode = HttpStatusCode.OK):CommandResult() {
    override val commandStatus: CommandStatus = CommandStatus.OK

}