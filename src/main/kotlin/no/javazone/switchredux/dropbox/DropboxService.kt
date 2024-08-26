package no.javazone.switchredux.dropbox

import com.dropbox.core.*
import com.dropbox.core.v2.*
import com.dropbox.core.v2.files.*
import no.javazone.switchredux.*
import no.javazone.switchredux.slack.*
import no.javazone.switchredux.slide.*
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

class ImageSlide(
    val imagePathList:List<String>,
):Slide(SlideType.IMAGE_SLIDE)



object DropboxService {
    val pathListStore:AtomicReference<List<String>> = AtomicReference(emptyList())
    val dropboxAccessToken:AtomicReference<Pair<String,LocalDateTime>?> = AtomicReference(null)

    val serviceThread:Thread = Thread {
        var doRun = true
        while (doRun) {
            val accessTokenPair = dropboxAccessToken.get()
            val accessToken = accessTokenPair?.first
            if (accessToken != null) {
                try {
                    readFilenames(accessToken)
                } catch (e:Exception) {
                    pathListStore.set(emptyList())
                    dropboxAccessToken.set(null)
                    SlackService.writeMessage("Error with access token. Read at ${accessTokenPair.second}. Error : ${e.message}")
                }
            }
            try {
                Thread.sleep(10_000)
            } catch (e:InterruptedException) {
                doRun = false
            }
            if (doRun && Thread.currentThread().isInterrupted) {
                doRun = false
            }
        }
    }.let {
        it.start()
        it
    }



    private fun readFilenames(accessToken:String) {
        val config = DbxRequestConfig.newBuilder("KotlinDropboxExample").build()
        val client = DbxClientV2(config, accessToken)
        val files = client.files()
        val listResult: ListFolderResult = files.listFolder("")
        val pathlist = listResult.entries.map {
            it.pathLower
        }
        pathListStore.set(pathlist)
    }

    fun nextSlide():ImageSlide? {
        val pathList = pathListStore.get()
        if (pathList.size < 3) {
            return null
        }
        val imagePathList:List<String> = pathList
            .shuffled()
            .subList(0,3)
            .map {
                val prefix = if (SetupValue.RUN_FROM_JAR.readBoolValue()) "" else "http://localhost:8080"
                "$prefix/dropbox$it"
            }
        return ImageSlide(imagePathList)
    }
}