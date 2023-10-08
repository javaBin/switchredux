package no.javazone.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import org.jsonbuddy.JsonArray
import org.jsonbuddy.JsonObject
import java.util.UUID

fun Application.configureRouting() {
    routing {
        get("/") {
            val toRespond = JsonObject()
                .put("id",UUID.randomUUID().toString())
                .put("slideList",JsonArray.fromNodeList(
                    listOf(
                        JsonObject().put("type","title").put("titleText","Welcome to JavaZone"),
                        JsonObject().put("type","content").put("titleText","Overskrift").put("contextTexts",JsonArray.fromStrings("One","Two","Three")),
                        JsonObject().put("type","title").put("titleText","See you at the party")
                    )
                ))


            call.respondText(toRespond.toJson(), ContentType.Application.Json, HttpStatusCode.OK)
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
