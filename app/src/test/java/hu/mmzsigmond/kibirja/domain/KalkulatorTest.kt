package hu.mmzsigmond.kibirja.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class KalkulatorTest {

    @Test
    fun kibirja() {
        // 2 ora video (24%) + 8 ora keszenlet (8%) = 32%
        val e = szamol(Terv(80.0, 10.0, listOf(Tevekenyseg("Videó", 2.0, 12.0)), 20.0))
        assertEquals(32.0, e.fogyasztas, 0.001)
        assertEquals(48.0, e.maradek, 0.001)
        assertTrue(e.tarthato)
        // 3.2% orankent, 60% van a tartalekig
        assertEquals(18.75, e.maxIdo, 0.001)
    }

    @Test
    fun nemBirjaKi() {
        // 3 ora jatek (60%) + 2 ora zene (8%) + 5 ora keszenlet (5%) = 73%, marad 7%
        val tevekenysegek = listOf(Tevekenyseg("Zene", 2.0, 4.0), Tevekenyseg("Játék", 3.0, 20.0))
        val e = szamol(Terv(80.0, 10.0, tevekenysegek, 20.0))
        assertFalse(e.tarthato)
        // 13% hianyzik, a jatek orankent 19%-ot sporol
        assertEquals("Játék", e.javaslatok[0].first)
        assertEquals(13.0 / 19.0, e.javaslatok[0].second, 0.001)
    }

    @Test
    fun hianyzoAdat() {
        val e = ellenoriz("", "abc", "20", emptyList())
        assertNull(e.terv)
        assertEquals(2, e.hibak.size)
    }

    @Test
    fun tulSokOra() {
        val e = ellenoriz("80", "2", "20", listOf(TevekenysegSor("Videó", "3", "12")))
        assertNull(e.terv)
    }

    @Test
    fun vesszo() {
        val e = ellenoriz("80", "10", "20", listOf(TevekenysegSor("Videó", "1,5", "12")))
        assertEquals(1.5, e.terv?.tevekenysegek?.get(0)?.orak)
    }
}
