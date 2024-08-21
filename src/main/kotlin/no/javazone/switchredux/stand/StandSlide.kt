package no.javazone.switchredux.stand

import no.javazone.switchredux.slide.*

data class StandPerson(
    val name:String,
    val chatList:List<String>
)

data class StandSlide(
    val personList:List<StandPerson>,
):Slide(SlideType.STAND_INFO) {
}