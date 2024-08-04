package no.javazone.switchredux.program

import org.assertj.core.api.Assertions
import org.jsonbuddy.JsonObject
import org.junit.Test
import java.time.*

class ProgramServiceTest {
    @Test
    fun shouldGiveWednesdayFirstSlot() {
        val programInput = JsonObject.read(this::class.java.classLoader.getResourceAsStream("program2023.json"))
        Assertions.assertThat(programInput).isNotNull()

        val res = ProgramService.giveSnapshot(programInput, LocalDateTime.of(2023,9,6,8,55))
        Assertions.assertThat(res).isNotNull()
    }
}