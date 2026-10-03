package hu.mmzsigmond.kibirja.domain

data class EllenorzesEredmeny(
    val terv: Terv?, // null, ha van hiba
    val hibak: List<String>,
)

fun ellenoriz(
    toltottsegSzoveg: String,
    osszIdoSzoveg: String,
    tartalekSzoveg: String,
    sorok: List<TevekenysegSor>,
): EllenorzesEredmeny {
    val hibak = mutableListOf<String>()
    val toltottseg = szam(toltottsegSzoveg)
    val osszIdo = szam(osszIdoSzoveg)
    val tartalek = szam(tartalekSzoveg)

    if (toltottseg == null || toltottseg <= 0 || toltottseg > 100) {
        hibak.add("Töltöttség: adj meg egy számot 1 és 100 között.")
    }
    if (osszIdo == null || osszIdo <= 0 || osszIdo > 72) {
        hibak.add("Időtartam: adj meg egy számot 0 és 72 óra között.")
    }
    if (tartalek == null || tartalek < 0 || tartalek >= 100) {
        hibak.add("Tartalék: adj meg egy számot 0 és 99 között.")
    } else if (toltottseg != null && tartalek >= toltottseg) {
        hibak.add("A tartalék nem lehet több a jelenlegi töltöttségnél.")
    }

    val tevekenysegek = mutableListOf<Tevekenyseg>()
    for ((i, sor) in sorok.withIndex()) {
        val nev = sor.nev.ifBlank { "${i + 1}. tevékenység" }
        val orak = szam(sor.orak)
        val fogyasztas = szam(sor.fogyasztas)

        if (sor.nev.isBlank()) hibak.add("A(z) ${i + 1}. tevékenységnek nincs neve.")
        if (orak == null || orak <= 0) hibak.add("$nev: hibás időtartam.")
        if (fogyasztas == null || fogyasztas <= 0 || fogyasztas > 100) hibak.add("$nev: hibás fogyasztás.")

        if (orak != null && fogyasztas != null) tevekenysegek.add(Tevekenyseg(nev, orak, fogyasztas))
    }

    if (osszIdo != null && tevekenysegek.sumOf { it.orak } > osszIdo) {
        hibak.add("A tevékenységek együtt több órásak, mint a megadott időtartam.")
    }

    if (hibak.isNotEmpty() || toltottseg == null || osszIdo == null || tartalek == null) {
        return EllenorzesEredmeny(null, hibak)
    }
    return EllenorzesEredmeny(Terv(toltottseg, osszIdo, tevekenysegek, tartalek), hibak)
}

// vesszot is elfogad, pl. 1,5
private fun szam(szoveg: String): Double? = szoveg.trim().replace(',', '.').toDoubleOrNull()
