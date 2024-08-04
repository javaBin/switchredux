package no.javazone.switchredux.program

import no.javazone.switchredux.*
import no.javazone.switchredux.slide.*

data class ProgramSnapshot(
    val startsAt:String,
    val roomList:List<RoomSnapshot>
):Slide(SlideType.NEXT_SLOT)

data class RoomSnapshot(
    val roomName:String,
    val talkList:List<TalkSnapshot>
)

data class TalkSnapshot(
    val title:String,
    val speaker:String
)