package no.javazone.switchredux.program

import org.jsonbuddy.*
import java.net.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.jvm.optionals.*
import kotlin.math.*

private data class SlotItem(
    val title:String,
    val speakers: JsonArray,
    val room:String,
    val startSlot:LocalDateTime,
)

object ProgramService {
    private fun toSlotItem(talkObject:JsonObject,now:LocalDateTime):SlotItem? {
        if (!setOf("presentation","lightning-talk").contains(talkObject.stringValue("format").getOrNull())) {
            return null
        }
        val slotTime:LocalDateTime? = talkObject.stringValue("startSlot").getOrNull()?.let { LocalDateTime.parse(it)}
        if (slotTime == null || slotTime.dayOfMonth != now.dayOfMonth) {
            return null
        }
        val minBetween:Long = abs(ChronoUnit.MINUTES.between(slotTime,now))
        if (minBetween > 10L) {
            return null
        }
        val room:String = talkObject.stringValue("room").getOrNull()?:return null
        return SlotItem(
            title = talkObject.requiredString("title"),
            speakers = talkObject.requiredArray("speakers"),
            room = room,
            startSlot = slotTime
        )
    }

    fun giveSnapshot(programInfo:JsonObject,now:LocalDateTime):ProgramSnapshot? {
        val rawTalkList:List<JsonObject> = programInfo.requiredArray("sessions").objects { it }
        val allTalkList:List<SlotItem> = rawTalkList.mapNotNull { toSlotItem(it,now) }
        if (allTalkList.isEmpty()) {
            return null
        }
        val slotTime:LocalDateTime = allTalkList.first().startSlot

        val roomList:List<RoomSnapshot> = (1..8).map { roomNo ->
            val roomName = "Room $roomNo"
            val talkList:List<TalkSnapshot> = allTalkList
                .filter { it.room == roomName }
                .map { slotItem ->
                    val speaker:String = slotItem.speakers.objects { it.requiredString("name") }.joinToString(separator = " and ")
                    TalkSnapshot(
                        title = slotItem.title,
                        speaker= speaker
                    )
                }
            RoomSnapshot(roomName,talkList)
        }
        return ProgramSnapshot(slotTime.toString(),roomList)
    }

    fun loadProgram():JsonObject = JsonObject.read(URI.create("https://sleepingpill.javazone.no/public/allSessions/javazone_2023").toURL())
}