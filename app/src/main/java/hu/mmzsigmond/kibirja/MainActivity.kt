/*
 * Kibirja? - akkumulator-tervezo (PENdroid 2026, 1. fordulo)
 * Csapat: #include <victory.h>
 *
 * Plusz funkciok:
 *  -
 */
package hu.mmzsigmond.kibirja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import hu.mmzsigmond.kibirja.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    Text("Kibírja?", modifier = Modifier.padding(padding))
                }
            }
        }
    }
}
