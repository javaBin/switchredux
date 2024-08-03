package no.javazone

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.jetty.*
import io.ktor.server.routing.*
import no.javazone.plugins.*

fun main() {
    Database.migrateWithFlyway()
    embeddedServer(Jetty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    //configureSecurity()
    configureHTTP()
    configureSerialization()
    //configureTemplating()
    configureRouting()
}
