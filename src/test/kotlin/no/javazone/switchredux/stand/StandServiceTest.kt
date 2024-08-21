package no.javazone.switchredux.stand

import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.InputStream
import kotlin.test.*

class StandServiceTest {
    @Test
    fun shouldLoad() {
        val inpstr:InputStream? = {}::class.java.classLoader.getResourceAsStream("private/standfile.txt")
        if (inpstr == null) {
            fail("No inputstream")

        }
        val res = StandService.loadStandSlots(inpstr)
        Assertions.assertThat(res).isNotEmpty()
    }
}