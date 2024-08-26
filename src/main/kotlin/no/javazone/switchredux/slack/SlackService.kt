package no.javazone.switchredux.slack

import no.javazone.switchredux.*
import no.javazone.switchredux.slide.*
import org.jsonbuddy.*
import org.slf4j.LoggerFactory
import java.io.*
import java.math.*
import java.net.*
import java.util.concurrent.atomic.AtomicReference

enum class SlackCommandType {
    ADD,
    LIST,
    RESET,
    UNKNOWN
}

data class SlackCommand(
    val command:SlackCommandType,
    val content:String,
    val timestamp:String
)

data class SlackMessageInfo(
    val slideList:List<TitleSlide>,
    val currentIndex:Int,
    val currentSlide:TitleSlide?
) {
    fun computeNext():SlackMessageInfo {
        if (slideList.isEmpty()) {
            return this
        }
        if (currentSlide == null) {
            return this.copy(currentSlide = slideList[0])
        }
        val nextIndex = currentIndex+1
        if (nextIndex >= slideList.size) {
            return this.copy(currentIndex = 0,currentSlide = null)
        }
        return this.copy(currentIndex = nextIndex,currentSlide = slideList[nextIndex])
    }

    fun addSlide(slide:TitleSlide):SlackMessageInfo {

        if (slideList.isEmpty()) {
            return SlackMessageInfo(
                listOf(slide),
                0,
                slide
            )
        }
        val copy = this.copy(slideList = slideList.plus(slide))
        return copy
    }

}

object SlackService {


    private val slideDeck:AtomicReference<SlackMessageInfo> = AtomicReference(SlackMessageInfo(emptyList(),0,null))

    fun currentSlide():Slide? {
        val slackMessageInfo:SlackMessageInfo = slideDeck.getAndUpdate { old -> old.computeNext() }
        return slackMessageInfo.currentSlide
    }

    fun addMessage(message:String) {
        slideDeck.updateAndGet { current -> current.addSlide(TitleSlide(message)) }
    }

    private fun reportMessages() {
        val slideDeck = slideDeck.get()
        if (slideDeck.slideList.isEmpty()) {
            writeMessage("No slides added. Slide deck empty")
            return
        }
        val text = StringBuilder("Got ${slideDeck.slideList.size} slides")
        for ((index,slide) in slideDeck.slideList.withIndex()) {
            text.append("\n${index+1}: ${slide.titleText}")
        }
        writeMessage(text.toString())
    }

    private fun clearSlides() {
        slideDeck.set(SlackMessageInfo(emptyList(),0,null))
        writeMessage("Slides cleared")
    }

    val slackServiceThread = Thread {

        var doRun = true
        var lastReadCommandTs:String = BigDecimal.valueOf(System.currentTimeMillis()).divide(BigDecimal(1000.00)).toString()
        while (doRun) {
            val commandList:List<SlackCommand> = readCommandFromSlack(lastReadCommandTs)
            lastReadCommandTs = commandList.lastOrNull()?.timestamp?:lastReadCommandTs
            for (command in commandList) {
                when (command.command) {
                    SlackCommandType.ADD -> {
                        addMessage(command.content)
                        writeMessage("Added message ${command.content}")
                    }
                    SlackCommandType.LIST -> reportMessages()
                    SlackCommandType.RESET -> clearSlides()
                    SlackCommandType.UNKNOWN -> writeMessage("Got command ${command.command} with text '$command.content' at ${command.timestamp} ")
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

    private fun nodeToCommand(jsonNode: JsonNode,slackUserId:String,startAt:String):SlackCommand? {
        if (jsonNode !is JsonObject) {
            return null
        }
        val timestamp = jsonNode.stringValue("ts").orElse("")
        if (timestamp <= startAt) {
            return null
        }
        val text = jsonNode.stringValue("text").orElse(null)?:return null
        val prefix = "<@$slackUserId>"
        if (!text.startsWith(prefix)) {
            return null
        }
        val textWithoutPrefix = text.removePrefix(prefix).trim()
        val command:SlackCommandType = SlackCommandType.entries.firstOrNull { textWithoutPrefix.uppercase().startsWith(it.name) }?:SlackCommandType.UNKNOWN
        val content = if (command == SlackCommandType.UNKNOWN) textWithoutPrefix else textWithoutPrefix.substring(command.name.length).trim()

        return SlackCommand(command,content,timestamp)

    }

    fun readCommandFromSlack(startAt: String):List<SlackCommand> {
        val token = SetupValue.SLACK_TOKEN.valueOrNull()?:return emptyList()
        val channelId = SetupValue.SLACK_CHANNELID.valueOrNull()?:return emptyList()
        val slackUserId = SetupValue.SLACK_BOT_USERID.valueOrNull()?:return emptyList()

        val url = URI.create("https://slack.com/api/conversations.history?channel=$channelId").toURL()
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Authorization", "Bearer $token")
        connection.setRequestProperty("Content-type", "application/json")
        val responseCode = connection.responseCode
        if (responseCode >= 300) {
            return emptyList()
        }
        val resoponseObj:JsonObject = JsonObject.read(connection.inputStream)
        val messageJsonArray:JsonArray = resoponseObj.arrayValue("messages").orElse(null)?:return emptyList()

        val result = messageJsonArray.mapNotNull { nodeToCommand(it,slackUserId,startAt) }
        return result.sortedBy { it.timestamp }
    }

    fun writeMessage(message:String) {
        val token:String? = SetupValue.SLACK_TOKEN.valueOrNull()
        val channelId:String?    = SetupValue.SLACK_CHANNELID.valueOrNull()
        if (token == null || channelId == null) {
            logger.info("SlackMessage: $message")
            return
        }

        val url = URI.create("https://slack.com/api/chat.postMessage").toURL()
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Authorization", "Bearer $token")
        connection.setRequestProperty("Content-type", "application/json")
        val inputJsonobj = JsonObject().put("channel",channelId).put("text",message)
        val jsonInputString = inputJsonobj.toJson()
        connection.doOutput = true
        connection.outputStream.use { os: OutputStream ->
            val input = jsonInputString.toByteArray()
            os.write(input, 0, input.size)
        }

        val responseCode = connection.responseCode
        if (responseCode != HttpURLConnection.HTTP_OK) {
            val errorStream = BufferedReader(InputStreamReader(connection.errorStream))
            val errorResponse = StringBuilder()
            errorStream.forEachLine { errorResponse.append(it) }
            errorStream.close()
            println("Unexpected response code: $responseCode. Error: $errorResponse")
            return
        }

        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()
        reader.forEachLine { response.append(it) }
        reader.close()


    }

    private val logger = LoggerFactory.getLogger(SlackService::class.java)

}