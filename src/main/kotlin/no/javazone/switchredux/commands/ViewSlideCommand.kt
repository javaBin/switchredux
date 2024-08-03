package no.javazone.switchredux.commands

import no.javazone.switchredux.*
import no.javazone.switchredux.slide.*

class ViewSlideCommand:Command {
    override fun execute(): Slide {
        val slide = SlideService.currentSlide()
        return slide
    }
}