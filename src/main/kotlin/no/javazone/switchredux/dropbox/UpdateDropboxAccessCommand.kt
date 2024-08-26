package no.javazone.switchredux.dropbox

import no.javazone.switchredux.*
import no.javazone.switchredux.time.*

class UpdateDropboxAccessCommand(
    val adminPassword:String? = null,
    val dropboxAccessToken:String? = null,
):Command {
    override fun execute(): CommandResult {
        if (adminPassword != SetupValue.ADMIN_PASSWORD.readValue()) {
            return CommandError(HttpStatusCode.UNAUTHORIZED,"Unauthorised")
        }
        if (dropboxAccessToken == null) {
            return CommandError(
                HttpStatusCode.BAD_REQUEST,
                "Missing drobpoxaccesstoken"
            )
        }
        DropboxService.dropboxAccessToken.set(Pair(dropboxAccessToken,TimeService.currentTime()))
        return EmptyResult()
    }
}