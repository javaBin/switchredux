package no.javazone

import no.javazone.slide.*
import org.jsonbuddy.pojo.JsonGenerator
import org.junit.Test

class SlideTest {
    @Test
    fun shouldConvert() {
        val titleSlide = TitleSlide("Welcome to javazone")
        val jsonObject = JsonGenerator.generate(titleSlide)
        println(jsonObject)
    }
}