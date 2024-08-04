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
            val programJson = ProgramService.loadProgram()
            val slide = ProgramService.giveSnapshot(programJson, LocalDateTime.of(2023,9,6,8,55))
            current.set(slide)
            /*
            while (true) {
                Thread.sleep(12000L)
                current.set(TitleSlide("Updated slide"))
                Thread.sleep(12000L)
                current.set(TitleSlide("Another slide"))
            }*/
        }.start()


    }
}