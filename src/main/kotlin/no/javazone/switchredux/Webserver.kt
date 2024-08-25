package no.javazone.switchredux

import no.javazone.switchredux.slack.*
import no.javazone.switchredux.slide.*
import org.eclipse.jetty.server.*
import org.eclipse.jetty.server.handler.*
import org.eclipse.jetty.servlet.*
import org.eclipse.jetty.util.resource.*
import org.eclipse.jetty.webapp.*
import org.slf4j.*

class Webserver {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Setup.loadValues(args)
            val logger = LoggerFactory.getLogger(Webserver::class.java)
            logger.info("Starting Webserver")
            SlackService.writeMessage("Starting up")
            SlideService.startup()
            if (SetupValue.USE_DB.readBoolValue()) {
                Database.migrateWithFlyway()
            }
            val slackThreadid =  SlackService.slackServiceThread.id
            logger.info("Slack service started with threadid $slackThreadid")
            Webserver().start()
            Runtime.getRuntime().addShutdownHook(Thread {
                SlackService.writeMessage("Staring shutdown")
                println("Shutdown hook triggered. Initiating graceful shutdown...")
                SlideService.slideLoaderThread.interrupt()
                SlackService.slackServiceThread.interrupt()
                SlideService.slideLoaderThread.join()
                SlackService.slackServiceThread.join()
                println("Graceful shutdown complete")
                SlackService.writeMessage("Shutdown")
            })
        }
    }

    fun start() {

        val server = Server(SetupValue.SERVER_PORT.readLongValue().toInt())
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

        if (SetupValue.RUN_FROM_JAR.readBoolValue()) {
            // Prod ie running from jar
            webAppContext.baseResource =
                Resource.newClassPathResource("static", false, false)
        } else {
            // Development ie running in ide
            webAppContext.resourceBase = "src/main/resources/static"
        }

        webAppContext.addServlet(ServletHolder(ApiServlet()), "/api/*")
        webAppContext.addServlet(ServletHolder(ImageServlet()), "/dropbox/*")
        return webAppContext
    }
}