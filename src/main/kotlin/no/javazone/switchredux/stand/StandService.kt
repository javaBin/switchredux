package no.javazone.switchredux.stand

import java.util.concurrent.atomic.*

object StandService {
    private val standSlide:StandSlide = StandSlide(listOf(
        StandPerson(
            name = "Peder",
            chatList = listOf("Azure", "Distributed computing")
        ),
        StandPerson(
            name = "Sverre",
            chatList = listOf("Java","Java FX","Api","Cloud")
        )
    ))


    fun readSlide():StandSlide {
        return standSlide
    }
}