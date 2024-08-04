package no.javazone.switchredux.slide

import org.jsonbuddy.*
import org.jsonbuddy.pojo.OverridesJsonGenerator

enum class SlideType(val text: String):OverridesJsonGenerator {
    TITLE("title"),
    CONTENT("content"),
    NEXT_SLOT("next-slot"),
    ;

    override fun jsonValue(): JsonString = JsonString(text)
}