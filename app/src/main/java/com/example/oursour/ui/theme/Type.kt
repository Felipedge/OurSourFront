package com.example.oursour.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.oursour.R

// 1. Fuente del texto: Montserrat (res/font). Va primero porque las otras la usan
val FuenteTexto = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

// 2. Fuente de los títulos: también Montserrat
val FuenteTitulos = FuenteTexto

// 3. Fuente del logo "Our Sour": Abril Fatface
val FuenteLogo = FontFamily(
    Font(R.font.abril_fatface_regular, FontWeight.Normal)
)

private val base = Typography()

val Typography = Typography(
    displayLarge = base.displayLarge.copy(fontFamily = FuenteTitulos),
    displayMedium = base.displayMedium.copy(fontFamily = FuenteTitulos),
    displaySmall = base.displaySmall.copy(fontFamily = FuenteTitulos),
    headlineLarge = base.headlineLarge.copy(fontFamily = FuenteTitulos),
    headlineMedium = base.headlineMedium.copy(fontFamily = FuenteTitulos),
    headlineSmall = base.headlineSmall.copy(fontFamily = FuenteTitulos),
    titleLarge = base.titleLarge.copy(fontFamily = FuenteTitulos),
    titleMedium = base.titleMedium.copy(fontFamily = FuenteTitulos),
    titleSmall = base.titleSmall.copy(fontFamily = FuenteTitulos),
    bodyLarge = base.bodyLarge.copy(fontFamily = FuenteTexto),
    bodyMedium = base.bodyMedium.copy(fontFamily = FuenteTexto),
    bodySmall = base.bodySmall.copy(fontFamily = FuenteTexto),
    labelLarge = base.labelLarge.copy(fontFamily = FuenteTexto),
    labelMedium = base.labelMedium.copy(fontFamily = FuenteTexto),
    labelSmall = base.labelSmall.copy(fontFamily = FuenteTexto)
)