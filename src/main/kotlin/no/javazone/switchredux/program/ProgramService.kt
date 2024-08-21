package no.javazone.switchredux.program

import no.javazone.switchredux.time.*
import org.jsonbuddy.*
import org.slf4j.LoggerFactory
import java.net.*
import java.time.LocalDateTime
import java.time.format.*
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

    private val loadedProgram:AtomicReference<JsonObject?> = AtomicReference(null)
    private val computedSlotItem:AtomicReference<Pair<LocalDateTime,ProgramSnapshot?>?> = AtomicReference(null)

    private val logger = LoggerFactory.getLogger(ProgramService::class.java)

    fun startUp() {

    }


    init {
        logger.info("Starting program service")
        Thread() {
            logger.info("Loading program data")
            val programJson = JsonObject.read(URI.create("https://sleepingpill.javazone.no/public/allSessions/javazone_2024").toURL())
            loadedProgram.set(programJson)
            val time = TimeService.currentTime()
            val slot = giveSnapshot(programJson, time)
            computedSlotItem.set(Pair(time,slot))
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

    private fun checkToUpdate():ProgramSnapshot? {
        val currentComputed:Pair<LocalDateTime,ProgramSnapshot?>? = computedSlotItem.get()
        val now = TimeService.currentTime()
        if (currentComputed?.first != null && currentComputed.first.plusMinutes(5).isAfter(now)) {
            return currentComputed.second
        }
        val programJson = loadedProgram.get()?:return null
        val newSlot = giveSnapshot(programJson, now)
        computedSlotItem.set(Pair(now,newSlot))
        return newSlot

    }



    fun getCurrentSlot():ProgramSnapshot? {
        val current = checkToUpdate()
        return current
    }

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

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
        return ProgramSnapshot(slotTime.format(timeFormatter),roomList)
    }



}