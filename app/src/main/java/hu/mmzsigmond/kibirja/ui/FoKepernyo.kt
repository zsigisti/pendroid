package hu.mmzsigmond.kibirja.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.mmzsigmond.kibirja.data.akkuSzazalek
import hu.mmzsigmond.kibirja.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun FoKepernyo(modifier: Modifier = Modifier, vm: TervViewModel = viewModel()) {
    val context = LocalContext.current
    val preview = LocalInspectionMode.current
    // indulaskor a telefon ertekevel indul, de atirhato
    LaunchedEffect(Unit) {
        if (!preview) vm.kezdoAkku(akkuSzazalek(context))
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Kibírja?", style = MaterialTheme.typography.headlineLarge)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Alapadatok", style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SzamMezo(
                        vm.toltottseg,
                        { vm.toltottseg = it },
                        "Jelenlegi töltöttség (%)",
                        Modifier.weight(1f),
                    )
                    TextButton(onClick = { vm.toltottseg = akkuSzazalek(context)?.toString() ?: "" }) {
                        Text("Lekérés")
                    }
                }
                SzamMezo(vm.osszIdo, { vm.osszIdo = it }, "Ennyi órát kell kibírnia")
                SzamMezo(vm.tartalek, { vm.tartalek = it }, "Tartalék a nap végére (%)")
            }
        }

        TevekenysegLista(vm.tevekenysegek)

        Button(
            onClick = { vm.szamolas() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Számolás")
        }

        if (vm.hibak.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Hiányos vagy hibás adatok", style = MaterialTheme.typography.titleMedium)
                    vm.hibak.forEach { Text("• $it") }
                }
            }
        }

        vm.eredmeny?.let { er ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        if (er.tarthato) "Kibírja!" else "Nem bírja ki",
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (er.tarthato) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    )
                    Text("Várható fogyasztás: ${er.fogyasztas.roundToInt()} %")
                    if (er.maradek < 0) {
                        Text("A nap végén marad: 0 % (a nap vége előtt lemerül)")
                    } else {
                        Text("A nap végén marad: ${er.maradek.roundToInt()} %")
                    }
                    Text("A tartalékig kb. ${"%.1f".format(er.maxIdo)} órát bír.")
                    if (er.javaslatok.isNotEmpty()) {
                        Text("Ennyivel kevesebbet használd:")
                        er.javaslatok.forEach { (nev, orak) ->
                            Text("• $nev: ${"%.1f".format(orak)} óra")
                        }
                    }
                    if (er.tolteniKell) {
                        Text(
                            "Így sem lesz meg a tartalék, közben tölteni kell.",
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun SzamMezo(
    ertek: String,
    valtozik: (String) -> Unit,
    cimke: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    OutlinedTextField(
        value = ertek,
        onValueChange = valtozik,
        label = { Text(cimke) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun FoKepernyoPreview() {
    AppTheme { FoKepernyo(vm = TervViewModel()) }
}
