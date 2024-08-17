package no.javazone.switchredux.time

import no.javazone.switchredux.*

class UpdateTransferedTimeCommand(
    val newOffset:String? = null,
): Command {
    override fun execute(): CommandResult {
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