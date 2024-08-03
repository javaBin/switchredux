package no.javazone.switchredux.slide

import no.javazone.switchredux.*
import java.util.UUID

abstract class Slide(val type: SlideType):CommandResult() {

    override val commandStatus: CommandStatus = CommandStatus.OK
    override val httpStatusCode: HttpStatusCode = HttpStatusCode.OK

    val id:String = UUID.randomUUID().toString()
}