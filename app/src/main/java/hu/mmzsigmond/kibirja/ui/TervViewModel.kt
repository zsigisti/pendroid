package hu.mmzsigmond.kibirja.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import hu.mmzsigmond.kibirja.domain.Eredmeny
import hu.mmzsigmond.kibirja.domain.TevekenysegSor
import hu.mmzsigmond.kibirja.domain.ellenoriz
import hu.mmzsigmond.kibirja.domain.szamol

// A ViewModel tulel egy elforgatast, igy nem vesznek el a beirt adatok.
class TervViewModel : ViewModel() {
    var toltottseg by mutableStateOf("")
    var osszIdo by mutableStateOf("")
    var tartalek by mutableStateOf("20")
    val tevekenysegek = mutableStateListOf<TevekenysegSor>()

    var hibak by mutableStateOf(emptyList<String>())
        private set
    var eredmeny by mutableStateOf<Eredmeny?>(null)
        private set

    private var akkuBeirva = false

    // csak az elso inditaskor irjuk be, utana mar a felhasznalo dont
    fun kezdoAkku(szazalek: Int?) {
        if (akkuBeirva) return
        akkuBeirva = true
        toltottseg = szazalek?.toString() ?: ""
    }

    fun szamolas() {
        val e = ellenoriz(toltottseg, osszIdo, tartalek, tevekenysegek)
        hibak = e.hibak
        // ha hibas, a regi eredmenyt is eltuntetjuk
        eredmeny = e.terv?.let { szamol(it) }
    }
}
