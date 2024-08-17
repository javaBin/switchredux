package no.javazone.switchredux.time

import java.time.*

interface TimeSource {
    fun currentTime(): LocalDateTime
    fun description():String
}