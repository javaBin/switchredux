package no.javazone.switchredux.time

import java.time.*

class FixedTimeSource:TimeSource {
    override fun currentTime(): LocalDateTime = LocalDateTime.of(2024,9,3,8,55,0)

    override fun description(): String = "Fixed time at September 3rd 08:55"

}