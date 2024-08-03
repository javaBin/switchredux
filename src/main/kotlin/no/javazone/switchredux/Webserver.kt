package no.javazone.switchredux

import org.eclipse.jetty.server.*
import org.eclipse.jetty.server.handler.*
import org.eclipse.jetty.servlet.*
import org.eclipse.jetty.util.resource.*
import org.eclipse.jetty.webapp.*

class Webserver {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Database.migrateWithFlyway()
            Webserver().start()
        }
    }

    fun start() {
        val server = Server(8080)
        val handlerCollection = ContextHandlerCollection()
        val handler = getHandler()
        handlerCollection.addHandler(handler)
        server.handler = handlerCollection
        server.start()
    }

    private fun getHandler():WebAppContext {
        val webAppContext = WebAppContext()

        webAppContext.getInitParams().put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false")
        webAppContext.setContextPath("/");

        if (Setup.readBoolValue(SetupValue.RUN_FROM_JAR)) {
            // Prod ie running from jar
            webAppContext.baseResource =
                Resource.newClassPathResource("static", false, false)
        } else {
            // Development ie running in ide
            webAppContext.resourceBase = "src/main/resources/static"
        }

        webAppContext.addServlet(ServletHolder(ApiServlet()), "/api/*")
        return webAppContext
    }
}