package no.javazone.switchredux.program

import org.jsonbuddy.*
import org.slf4j.LoggerFactory
import java.net.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.atomic.AtomicReference
import kotlin.jvm.optionals.*
import kotlin.math.*

private data class SlotItem(
    val title:String,
    val speakers: JsonArray,
    val room:String,
    val startSlot:LocalDateTime,
)

object ProgramService {
    private val fixedTime:LocalDateTime = LocalDateTime.of(2023,9,6,8,55)
    private val loadedProgram:AtomicReference<JsonObject?> = AtomicReference(null)
    private val computedSlotItem:AtomicReference<Pair<LocalDateTime,ProgramSnapshot?>?> = AtomicReference(null)

    private val logger = LoggerFactory.getLogger(ProgramService::class.java)

    fun startUp() {

    }

    init {
        logger.info("Starting program service")
        Thread() {
            logger.info("Loading program data")
            val programJson = JsonObject.read(URI.create("https://sleepingpill.javazone.no/public/allSessions/javazone_2023").toURL())
            loadedProgram.set(programJson)
            val slot = giveSnapshot(programJson, fixedTime)
            computedSlotItem.set(Pair(fixedTime,slot))
            logger.info("Loaded program")
        }.start()
    }

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

    fun getCurrentSlot():ProgramSnapshot? {
        return computedSlotItem.get()?.second
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



}