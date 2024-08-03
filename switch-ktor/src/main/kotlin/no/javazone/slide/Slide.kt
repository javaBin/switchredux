package no.javazone.slide

import java.util.UUID

abstract class Slide(val type: SlideType) {


    val id:String = UUID.randomUUID().toString()
}