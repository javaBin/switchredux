package no.javazone.switchredux.stand

import no.javazone.switchredux.slide.*
import java.time.LocalDateTime

data class StandSlot(
    val startTime:LocalDateTime,
    val endTime:LocalDateTime,
    val standSlide: StandSlide
)

data class StandPerson(
    val name:String,
    val chatList:List<String>
)

data class StandSlide(
    val personList:List<StandPerson>,
):Slide(SlideType.STAND_INFO) {
}