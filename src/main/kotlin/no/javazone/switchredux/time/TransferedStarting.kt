package no.javazone.switchredux.time

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicReference

private class TransferTimeDefinition(
    val startAt:Long,
    val startTime:LocalDateTime,
)


class TransferredTimeSource:TimeSource {
    override fun currentTime(): LocalDateTime = giveTime()

    override fun description(): String = "Transfered time"

    companion object {
        private val transferTimeDefinition:AtomicReference<TransferTimeDefinition> = AtomicReference(TransferTimeDefinition(System.currentTimeMillis(),LocalDateTime.now().plusDays(1)))
        private val dateFormat = DateTimeFormatter.ofPattern("yyyMMddHHmm")

        private fun giveTime():LocalDateTime {
            val now = System.currentTimeMillis()
            val transferTimeDefinition = transferTimeDefinition.get()
            val diff = now - transferTimeDefinition.startAt
            val result = transferTimeDefinition.startTime.plusNanos(diff * 1_000_000L)
            return result
        }

        fun updateDefinition(timeNow:String) {
            val startTime:LocalDateTime = LocalDateTime.parse(timeNow,dateFormat)
            transferTimeDefinition.set(TransferTimeDefinition(System.currentTimeMillis(),startTime))
        }
    }
}