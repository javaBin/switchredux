package no.javazone.switchredux.slide

import java.time.LocalDateTime

data class SliteItemTime(
    val from:LocalDateTime,
    val to:LocalDateTime,
)

enum class SlideGeneratorType {
    ALWAYS,
    MANAGER,
    LISTEN
}

data class SlideItemGenerator(
    val factory:() -> Slide?,
    val displayMillis:Long,
    val callMeAgain:Boolean = false,
    val time:SliteItemTime? = null,
    val slideGeneratorType: SlideGeneratorType,
) {
}