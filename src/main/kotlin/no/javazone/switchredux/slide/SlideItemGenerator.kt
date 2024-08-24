package no.javazone.switchredux.slide

data class SlideItemGenerator(
    val factory:() -> Slide?,
    val displayMillis:Long,
    val callMeAgain:Boolean = false,
) {
}