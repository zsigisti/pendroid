package hu.mmzsigmond.kibirja.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class KalkulatorTest {

    @Test
    fun kibirja() {
        val e = szamol(Terv(80.0, 10.0, listOf(Tevekenyseg("Videó", 2.0, 12.0)), 20.0))
        assertEquals(32.0, e.fogyasztas, 0.001)
        assertEquals(48.0, e.maradek, 0.001)
        assertTrue(e.tarthato)
        assertEquals(18.75, e.maxIdo, 0.001)
    }

    @Test
    fun nemBirjaKi() {
        val tevekenysegek = listOf(Tevekenyseg("Zene", 2.0, 4.0), Tevekenyseg("Játék", 3.0, 20.0))
        val e = szamol(Terv(80.0, 10.0, tevekenysegek, 20.0))
        assertFalse(e.tarthato)
        assertEquals("Játék", e.javaslatok[0].first)
        assertEquals(13.0 / 19.0, e.javaslatok[0].second, 0.001)
    }

    @Test
    fun tolteniKell() {
        // 30%-rol 20 ora keszenlet is 20%, a 15% tartalek mar nem jon ki
        val e = szamol(Terv(30.0, 20.0, listOf(Tevekenyseg("Játék", 2.0, 20.0)), 15.0))
        assertFalse(e.tarthato)
        assertTrue(e.tolteniKell)
    }

    @Test
    fun javaslattalKijon() {
        val tevekenysegek = listOf(Tevekenyseg("Zene", 2.0, 4.0), Tevekenyseg("Játék", 3.0, 20.0))
        assertFalse(szamol(Terv(80.0, 10.0, tevekenysegek, 20.0)).tolteniKell)
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
