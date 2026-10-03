package hu.mmzsigmond.kibirja.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import hu.mmzsigmond.kibirja.R

// Nunito (res/font/nunito.ttf), egy fajlban van minden vastagsag
@OptIn(ExperimentalTextApi::class)
private fun nunito(weight: FontWeight) = Font(
    R.font.nunito,
    weight = weight,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
)

val Nunito = FontFamily(
    nunito(FontWeight.Normal),
    nunito(FontWeight.Medium),
    nunito(FontWeight.SemiBold),
    nunito(FontWeight.Bold),
)

private val alap = Typography()

val AppTypography = Typography(
    displayLarge = alap.displayLarge.copy(fontFamily = Nunito),
    displayMedium = alap.displayMedium.copy(fontFamily = Nunito),
    displaySmall = alap.displaySmall.copy(fontFamily = Nunito),
    headlineLarge = alap.headlineLarge.copy(fontFamily = Nunito, fontWeight = FontWeight.Bold),
    headlineMedium = alap.headlineMedium.copy(fontFamily = Nunito, fontWeight = FontWeight.Bold),
    headlineSmall = alap.headlineSmall.copy(fontFamily = Nunito, fontWeight = FontWeight.Bold),
    titleLarge = alap.titleLarge.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold),
    titleMedium = alap.titleMedium.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold),
    titleSmall = alap.titleSmall.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold),
    bodyLarge = alap.bodyLarge.copy(fontFamily = Nunito),
    bodyMedium = alap.bodyMedium.copy(fontFamily = Nunito),
    bodySmall = alap.bodySmall.copy(fontFamily = Nunito),
    labelLarge = alap.labelLarge.copy(fontFamily = Nunito),
    labelMedium = alap.labelMedium.copy(fontFamily = Nunito),
    labelSmall = alap.labelSmall.copy(fontFamily = Nunito),
)
