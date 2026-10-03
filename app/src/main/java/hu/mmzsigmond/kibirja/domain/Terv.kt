package hu.mmzsigmond.kibirja.domain

// Ahogy a mezokbe be van irva, szovegkent
data class TevekenysegSor(
    val nev: String = "",
    val orak: String = "",
    val fogyasztas: String = "",
    val egyeb: Boolean = false, // csak az egyeb tevekenyseg nevet lehet atirni
)

data class Tevekenyseg(
    val nev: String,
    val orak: Double,
    val fogyasztas: Double, // %/ora
)

data class Terv(
    val toltottseg: Double, // %
    val osszIdo: Double, // ora
    val tevekenysegek: List<Tevekenyseg>,
    val tartalek: Double, // %
)