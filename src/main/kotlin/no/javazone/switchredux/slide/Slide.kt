package no.javazone.switchredux.slide

import no.javazone.switchredux.*
import java.util.UUID

abstract class Slide(val type: SlideType):CommandResult() {

    val id:String = UUID.randomUUID().toString()
}