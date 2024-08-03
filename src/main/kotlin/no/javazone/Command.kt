package no.javazone

import io.ktor.http.*
import org.jsonbuddy.JsonObject

interface Command {
    fun execute():CommandResult

    companion object {
        fun badRequest(errormessage:String):CommandError = CommandError(HttpStatusCode.BadRequest,errormessage)

    }
}

