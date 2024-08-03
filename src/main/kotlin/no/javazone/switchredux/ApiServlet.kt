package no.javazone.switchredux

import no.javazone.switchredux.commands.*
import javax.servlet.http.*

class ApiServlet:HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val command:Command? = when(req.pathInfo) {
            "/readSlide" -> ViewSlideCommand()
            else -> null
        }
        if (command == null) {
            resp.sendError(404)
            return
        }
        val commandResult = command.execute()
        resp.contentType = "application/json; charset=utf-8"
        resp.addHeader("Access-Control-Allow-Origin","http://localhost:3000")
        resp.addHeader("Access-Control-Allow-Headers","content-type,logintoken")
        resp.addHeader("Access-Control-Allow-Methods","GET,POST")
        commandResult.toJson().toJson(resp.writer)
    }

}