package no.javazone.util

import com.dropbox.core.*
import com.dropbox.core.v2.*
import com.dropbox.core.v2.files.ListFolderResult
import no.javazone.switchredux.*

class DropboxAccessTEst {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Setup.loadValues(args)
            val accessToken = SetupValue.DROPBOX_ACCESS.valueOrNull()
            if (accessToken == null) {
                println("No access token")
                return
            }
            val config = DbxRequestConfig.newBuilder("KotlinDropboxExample").build()
            val client = DbxClientV2(config, accessToken)
            val files = client.files()
            val listResult:ListFolderResult = files.listFolder("")
            println("List result: $listResult")
            val pathlist = listResult.entries.map {
                it.pathLower
            }
            println(pathlist)
        }
    }
}