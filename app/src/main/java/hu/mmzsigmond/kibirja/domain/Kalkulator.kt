package hu.mmzsigmond.kibirja.domain

// ennyit fogy orankent, amikor nem hasznaljuk a telefont
const val KESZENLET = 1.0

data class Eredmeny(
    val fogyasztas: Double, // %
    val maradek: Double, // %
    val tarthato: Boolean,
    val maxIdo: Double, // ennyi orat birja a tartalekig
    val javaslatok: List<Pair<String, Double>>, // tevekenyseg, ennyi oraval kevesebbet
)

fun szamol(terv: Terv): Eredmeny {
    var fogyasztas = 0.0
    var aktivOrak = 0.0
    for (t in terv.tevekenysegek) {
        fogyasztas += t.orak * t.fogyasztas
        aktivOrak += t.orak
    }
    // a maradek idoben keszenletben van
    fogyasztas += (terv.osszIdo - aktivOrak) * KESZENLET

    val maradek = terv.toltottseg - fogyasztas
    val tarthato = maradek >= terv.tartalek

    val orankent = fogyasztas / terv.osszIdo
    val maxIdo = (terv.toltottseg - terv.tartalek) / orankent

    // a legtobbet fogyasztobol vagunk le eloszor
    val javaslatok = mutableListOf<Pair<String, Double>>()
    var hiany = terv.tartalek - maradek
    for (t in terv.tevekenysegek.sortedByDescending { it.fogyasztas }) {
        if (hiany <= 0) break
        // ha nem ezt csinaljuk, keszenletben van, igy csak a kulonbseget sporoljuk
        val sporol = t.fogyasztas - KESZENLET
        if (sporol <= 0) continue
        val orak = minOf(t.orak, hiany / sporol)
        javaslatok.add(t.nev to orak)
        hiany -= orak * sporol
    }

    return Eredmeny(fogyasztas, maradek, tarthato, maxIdo, javaslatok)
}
