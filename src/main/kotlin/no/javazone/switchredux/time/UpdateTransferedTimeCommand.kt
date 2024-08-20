package no.javazone.switchredux.time

import no.javazone.switchredux.*

class UpdateTransferedTimeCommand(
    val newOffset:String? = null,
    val adminPassword:String? = null,
): Command {
    override fun execute(): CommandResult {
        if (adminPassword != SetupValue.ADMIN_PASSWORD.readValue()) {
            return CommandError(HttpStatusCode.UNAUTHORIZED,"Unauthorised")
        }
        if (newOffset == null) {
            return CommandError(
                HttpStatusCode.BAD_REQUEST,
                "Missing newoffset"
            )
        }
        TransferredTimeSource.updateDefinition(newOffset)

        return TimeService.currentTimeInfo()
    }
}