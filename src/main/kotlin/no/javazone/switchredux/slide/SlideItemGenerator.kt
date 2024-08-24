package no.javazone.switchredux.slide

import java.time.LocalDateTime

data class SliteItemTime(
    val from:LocalDateTime,
    val to:LocalDateTime,
)

data class SlideItemGenerator(
    val factory:() -> Slide?,
    val displayMillis:Long,
    val callMeAgain:Boolean = false,
    val time:SliteItemTime? = null,
) {
}