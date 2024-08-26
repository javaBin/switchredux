package no.javazone.switchredux.dropbox

import no.javazone.switchredux.*

class DropboxInfo(
    val hasAccess:Boolean,
    val accessReadAt:String?,
    val imageListSize:Int,
    val imageNameList:List<String>,
):CommandResult()

class ReadDropboxInfoCommand: Command {
    override fun execute(): DropboxInfo {
        val accessTokenPair = DropboxService.dropboxAccessToken.get()
        val filenameList = DropboxService.pathListStore.get()
        return DropboxInfo(
            accessTokenPair != null,
            accessTokenPair?.second?.toString(),
            filenameList.size,
            filenameList
        )
    }
}