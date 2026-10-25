package com.example.oursour.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.oursour.R

// Fuente de los títulos: DM Serif Display (res/font)
val FuenteTitulos = FontFamily(
    Font(R.font.dm_serif_display_regular, FontWeight.Normal),
    Font(R.font.dm_serif_display_italic, FontWeight.Normal, FontStyle.Italic)
)

val FuenteLogo = FontFamily(
    Font(R.font.abril_fatface_regular, FontWeight.Normal)
)


val FuenteTexto = FontFamily.Default

private val base = Typography()

val Typography = Typography(
    // Títulos grandes: saludos, nombre del producto, barra superior
    displayLarge = base.displayLarge.copy(fontFamily = FuenteTitulos),
    displayMedium = base.displayMedium.copy(fontFamily = FuenteTitulos),
    displaySmall = base.displaySmall.copy(fontFamily = FuenteTitulos),
    headlineLarge = base.headlineLarge.copy(fontFamily = FuenteTitulos),
    headlineMedium = base.headlineMedium.copy(fontFamily = FuenteTitulos),
    headlineSmall = base.headlineSmall.copy(fontFamily = FuenteTitulos),
    titleLarge = base.titleLarge.copy(fontFamily = FuenteTitulos),

    // Texto: nombres en tarjetas, descripciones, precios y botones
    titleMedium = base.titleMedium.copy(fontFamily = FuenteTexto),
    titleSmall = base.titleSmall.copy(fontFamily = FuenteTexto),
    bodyLarge = base.bodyLarge.copy(fontFamily = FuenteTexto),
    bodyMedium = base.bodyMedium.copy(fontFamily = FuenteTexto),
    bodySmall = base.bodySmall.copy(fontFamily = FuenteTexto),
    labelLarge = base.labelLarge.copy(fontFamily = FuenteTexto),
    labelMedium = base.labelMedium.copy(fontFamily = FuenteTexto),
    labelSmall = base.labelSmall.copy(fontFamily = FuenteTexto)
)