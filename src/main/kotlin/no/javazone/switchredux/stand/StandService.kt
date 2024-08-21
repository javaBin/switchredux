package no.javazone.switchredux.stand

import no.javazone.switchredux.time.*
import java.io.InputStream
import java.time.*


object StandService {
    private val slotList:List<StandSlot> = loadStandSlots(StandService::class.java.classLoader.getResourceAsStream("private/standfile.txt"))



    fun readSlide():StandSlide? {
        val now = TimeService.currentTime()
        for (slot in slotList) {
            if (now.isAfter(slot.startTime) && now.isBefore(slot.endTime)) {
                return slot.standSlide
            }
        }
        return null
    }

    fun loadStandSlots(inputStream:InputStream?):List<StandSlot> {
        if (inputStream == null) {
            return emptyList()
        }
        val lines:List<String> = inputStream.bufferedReader().use { it.readLines() }
        val result:MutableList<StandSlot> = mutableListOf()
        var currentDay = LocalDate.of(2024,9,4)

        for (line in lines) {
            if (line.startsWith("---")) {
                currentDay = currentDay.plusDays(1)
                continue
            }
            val parts = line.split(";")
            val start: LocalDateTime = currentDay.atTime(parts[0].substring(0,2).toInt(),parts[0].substring(2,4).toInt())
            val end: LocalDateTime = currentDay.atTime(parts[0].substring(5,7).toInt(),parts[0].substring(7,9).toInt())

            val personList:MutableList<StandPerson> = mutableListOf()

            var partno = -1
            while (true) {
                partno+=2
                if (partno >= parts.size) {
                    break
                }
                if (parts[partno].isEmpty()) {
                   continue
                }
                val nameparts = parts[partno].split(" ")
                val chatList = parts[partno+1].split(",")
                    .filterNot { it.trim().isEmpty() || it.uppercase() == "TBD" }.map { it.trim() }
                personList.add(StandPerson(name = nameparts[0],chatList = chatList))
            }
            result.add(StandSlot(start,end, StandSlide(personList)))
        }

        return result;
    }
}