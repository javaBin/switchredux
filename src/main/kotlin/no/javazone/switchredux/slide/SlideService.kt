package no.javazone.switchredux.slide

import no.javazone.switchredux.program.*
import no.javazone.switchredux.stand.*
import java.util.concurrent.atomic.AtomicReference


private val initSlideDeck:List<SlideItemGenerator> = listOf(

    SlideItemGenerator(
        factory = { TitleSlide("Welcome")},
        displayMillis = 3000L
    ),
    SlideItemGenerator(
        factory = {NoDataFromServerSlide(SlideType.PARTNER_SUMMARY)},
        displayMillis = 5_000L
    ),
    SlideItemGenerator(
        factory ={ProgramService.getCurrentSlot()},
        displayMillis = 15_000L
    ),

    SlideItemGenerator(
        factory = { NoDataFromServerSlide(SlideType.GAME_OF_LIFE) },
        displayMillis = 10_000L,
    ),
    SlideItemGenerator(
        factory = { StandService.readSlide() },
        displayMillis = 10_000L,
    )
)


object SlideService {
    private val currentDeck:AtomicReference<List<SlideItemGenerator>> = AtomicReference(initSlideDeck)
    private val current:AtomicReference<Slide> = AtomicReference(TitleSlide("From server test"))
    fun currentSlide(): Slide {
        return current.get()
    }

    fun startup() {
        ProgramService.startUp()
    }

    init {
        current.set(TitleSlide("Initial slide"))

        Thread {


            while (true) {
                val currentRun:List<SlideItemGenerator> = currentDeck.get()
                for (item in currentRun) {
                    val slide:Slide? = item.factory()
                    if (slide == null) {
                        continue
                    }
                    current.set(slide)
                    Thread.sleep(item.displayMillis)
                }
            }
        }.start()


    }
}