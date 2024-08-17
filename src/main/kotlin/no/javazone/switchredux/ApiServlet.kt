package no.javazone.switchredux

import no.javazone.switchredux.commands.*
import no.javazone.switchredux.time.*
import org.jsonbuddy.JsonObject
import org.jsonbuddy.pojo.PojoMapper
import javax.servlet.http.*
import kotlin.reflect.*

class ApiServlet:HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val command:Command? = when(req.pathInfo) {
            "/readSlide" -> ViewSlideCommand()
            "/time" -> ShowTimeCommand()
            else -> null
        }
        if (command == null) {
            resp.sendError(404)
            return
        }
        executeCommand(command, resp)
    }

    private fun executeCommand(command: Command, resp: HttpServletResponse) {
        val commandResult = command.execute()
        resp.contentType = "application/json; charset=utf-8"
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000")
        resp.addHeader("Access-Control-Allow-Headers", "content-type,logintoken")
        resp.addHeader("Access-Control-Allow-Methods", "GET,POST")
        commandResult.toJson().toJson(resp.writer)
    }


    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val inputBody = JsonObject.read(req.reader)
        val commandClass:KClass<out Command>? = when (req.pathInfo) {
            "/admin/updateTranferedTime" -> UpdateTransferedTimeCommand::class
            else -> null
        }
        if (commandClass == null) {
            resp.sendError(404)
            return
        }
        val command:Command = PojoMapper.map(inputBody,commandClass.java)
        executeCommand(command,resp)
    }

}