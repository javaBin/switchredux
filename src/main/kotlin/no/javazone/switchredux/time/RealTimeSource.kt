package no.javazone.switchredux.time

import java.time.*

class RealTimeSource: TimeSource {
    override fun currentTime(): LocalDateTime = LocalDateTime.now()
    override fun description():String = "RealTime"
}