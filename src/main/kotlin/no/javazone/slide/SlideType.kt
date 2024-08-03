package no.javazone.slide

import org.jsonbuddy.*
import org.jsonbuddy.pojo.OverridesJsonGenerator

enum class SlideType(val text: String):OverridesJsonGenerator {
    TITLE("title"),
    CONTENT("content"),
    ;

    override fun jsonValue(): JsonString = JsonString(text)
}