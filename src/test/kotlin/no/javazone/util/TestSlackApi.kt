package no.javazone.util

import no.javazone.switchredux.*
import org.jsonbuddy.JsonObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.time.LocalDateTime

class TestSlackApi {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Setup.loadValues(args)
            val token = SetupValue.SLACK_TOKEN.readValue()
            if (token.isEmpty()) {
                println("Need slack token")
                return
            }
            val channelId = SetupValue.SLACK_CHANNELID.readValue()
            if (channelId.isEmpty()) {
                println("Need slack channel id")
                return
            }
            //doCall(token,channelId)
            writeMessage(token,channelId)
        }

        fun doCall(token:String,channelId:String) {
            val url = URI.create("https://slack.com/api/conversations.history?channel=$channelId").toURL()
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("Content-type", "application/json")
            val responseCode = connection.responseCode
            if (responseCode >= 300) {
                println("Error resoponse $responseCode")
                return
            }
            val resoponseObj = JsonObject.read(connection.inputStream)
            println(resoponseObj)
        }

        fun writeMessage(token: String,channelId: String) {
            val url = URI.create("https://slack.com/api/chat.postMessage").toURL()
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("Content-type", "application/json")
            val inputJsonobj = JsonObject().put("channel",channelId).put("text","Hello from api. My time is ${LocalDateTime.now()}")
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

            println("Got response: $response")

        }
    }
}