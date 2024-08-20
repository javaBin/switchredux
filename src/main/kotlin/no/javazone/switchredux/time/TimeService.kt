package no.javazone.switchredux.time

import no.javazone.switchredux.*
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

object TimeService {
    private val timeSource:AtomicReference<TimeSource> by lazy {
        val clockType = ClockType.valueOf(SetupValue.TIME_SOURCE.readValue())
        AtomicReference(clockType.factory())
    }


    fun currentTime():LocalDateTime = timeSource.get().currentTime()

    fun currentTimeInfo():ShowTimeCommandResult {
        val currentTimeSource = timeSource.get()
        return ShowTimeCommandResult(
            time = currentTimeSource.currentTime().toString(),
            timeSource = currentTimeSource.description(),
        )
    }
}