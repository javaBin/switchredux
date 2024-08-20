package no.javazone.switchredux.stand

import no.javazone.switchredux.slide.*

data class StandSlide(
    val name:String,
    val chatList:List<String>
):Slide(SlideType.STAND_INFO) {
}