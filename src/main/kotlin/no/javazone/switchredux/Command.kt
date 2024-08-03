package no.javazone.switchredux

interface Command {
    fun execute(): CommandResult

    companion object {
        fun badRequest(errormessage:String): CommandError = CommandError(HttpStatusCode.BAD_REQUEST,errormessage)

    }
}

