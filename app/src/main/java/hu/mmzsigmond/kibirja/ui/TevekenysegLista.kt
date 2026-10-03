package hu.mmzsigmond.kibirja.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Ami a mezokbe be van irva, meg szovegkent.
data class TevekenysegSor(
    val nev: String = "",
    val orak: String = "",
    val fogyasztas: String = "",
)

// Elore megadott fogyasztasok, %/ora
private val alapTevekenysegek = listOf(
    "Videó" to 12.0,
    "Játék" to 20.0,
    "Zene" to 4.0,
    "Navigáció" to 15.0,
    "Böngészés" to 8.0,
    "Közösségi média" to 10.0,
    "Telefonálás" to 6.0,
    "Fényképezés" to 14.0,
)

@Composable
fun TevekenysegLista(tevekenysegek: SnapshotStateList<TevekenysegSor>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Tervezett tevékenységek", style = MaterialTheme.typography.titleMedium)

            if (tevekenysegek.isEmpty()) {
                Text("Még nincs tevékenység. Ha nem adsz meg semmit, készenléttel számolunk.")
            }

            tevekenysegek.forEachIndexed { i, t ->
                if (i > 0) HorizontalDivider()
                TevekenysegSorSzerkeszto(
                    t = t,
                    valtozik = { tevekenysegek[i] = it },
                    torol = { tevekenysegek.removeAt(i) },
                )
            }

            HozzaadasGomb { tevekenysegek.add(it) }
        }
    }
}

@Composable
private fun TevekenysegSorSzerkeszto(
    t: TevekenysegSor,
    valtozik: (TevekenysegSor) -> Unit,
    torol: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = t.nev,
                onValueChange = { valtozik(t.copy(nev = it)) },
                label = { Text("Név") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )
            TextButton(onClick = torol) { Text("Törlés") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SzamMezo(t.orak, { valtozik(t.copy(orak = it)) }, "Óra", Modifier.weight(1f))
            SzamMezo(t.fogyasztas, { valtozik(t.copy(fogyasztas = it)) }, "%/óra", Modifier.weight(1f))
        }
    }
}

@Composable
private fun HozzaadasGomb(hozzaad: (TevekenysegSor) -> Unit) {
    var nyitva by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { nyitva = true }) { Text("+ Tevékenység") }
        DropdownMenu(expanded = nyitva, onDismissRequest = { nyitva = false }) {
            alapTevekenysegek.forEach { (nev, fogyasztas) ->
                DropdownMenuItem(
                    text = { Text("$nev (${fogyasztas.toInt()} %/óra)") },
                    onClick = {
                        hozzaad(TevekenysegSor(nev, "1", fogyasztas.toInt().toString()))
                        nyitva = false
                    },
                )
            }
            DropdownMenuItem(
                text = { Text("Egyéb...") },
                onClick = {
                    hozzaad(TevekenysegSor())
                    nyitva = false
                },
            )
        }
    }
}
