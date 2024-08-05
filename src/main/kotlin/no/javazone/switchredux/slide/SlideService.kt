package no.javazone.switchredux.slide

import no.javazone.switchredux.program.*
import java.time.*
import java.util.concurrent.atomic.AtomicReference

object SlideService {
    private val current:AtomicReference<Slide> = AtomicReference(TitleSlide("From server test"))
    fun currentSlide(): Slide {
        return current.get()
    }

    init {
        current.set(TitleSlide("Initial slide"))

        Thread {

            while (true) {
                current.set(TitleSlide("Updated slide"))
                Thread.sleep(3000L)
                val programSlide = ProgramService.getCurrentSlot()
                if (programSlide != null) {
                    current.set(programSlide)
                    Thread.sleep(12000L)
                }
                current.set(TitleSlide("Another slide"))
            }
        }.start()


    }
}