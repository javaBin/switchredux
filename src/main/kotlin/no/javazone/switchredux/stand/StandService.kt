package no.javazone.switchredux.stand

import java.util.concurrent.atomic.*

object StandService {
    private val slideList:List<StandSlide> = listOf(
        StandSlide(
            name = "Peder",
            chatList = listOf("Azure", "Distributed computing")
        ),
        StandSlide(
            name = "Sverre",
            chatList = listOf("Java","Java FX","Api","Cloud")
        )
    )

    private val slideIndex: AtomicInteger = AtomicInteger(0)

    fun readSlide():StandSlide {
        val index:Int = slideIndex.getAndUpdate({
            if (it+1 >= slideList.size) 0 else it+1
        })
        return slideList[index]
    }
}