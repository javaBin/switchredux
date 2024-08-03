package no.javazone.slide

import java.util.concurrent.atomic.AtomicReference

object SlideService {
    private val current:AtomicReference<Slide> = AtomicReference(TitleSlide("From server test"))
    fun currentSlide():Slide {
        return current.get()
    }

    init {
        Thread {
            while (true) {
                Thread.sleep(12000L)
                current.set(TitleSlide("Updated slide"))
                Thread.sleep(12000L)
                current.set(TitleSlide("Another slide"))
            }
        }.start()


    }
}