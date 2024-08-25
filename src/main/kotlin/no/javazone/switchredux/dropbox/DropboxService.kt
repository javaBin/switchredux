package no.javazone.switchredux.dropbox

import com.dropbox.core.*
import com.dropbox.core.v2.*
import com.dropbox.core.v2.files.*
import no.javazone.switchredux.*
import no.javazone.switchredux.slide.*
import java.util.concurrent.atomic.AtomicReference

class ImageSlide(
    val imagePathList:List<String>,
):Slide(SlideType.IMAGE_SLIDE)

object DropboxService {
    val pathListStore:AtomicReference<List<String>> = AtomicReference(emptyList())

    init {
        Thread {
            val accessToken = SetupValue.DROPBOX_ACCESS.valueOrNull()
            if (accessToken != null) {
                val config = DbxRequestConfig.newBuilder("KotlinDropboxExample").build()
                val client = DbxClientV2(config, accessToken)
                val files = client.files()
                val listResult: ListFolderResult = files.listFolder("")
                val pathlist = listResult.entries.map {
                    it.pathLower
                }
                pathListStore.set(pathlist)
            }
        }.start()


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