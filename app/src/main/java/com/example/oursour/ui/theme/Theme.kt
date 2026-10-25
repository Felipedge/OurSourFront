package com.example.oursour.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaOurSour = lightColorScheme(
    primary = Primario,
    onPrimary = Color.White,
    primaryContainer = ContenedorPrimario,
    onPrimaryContainer = SobreContenedorPrimario,
    secondary = Secundario,
    onSecondary = SobreSecundario,
    secondaryContainer = ContenedorSecundario,
    onSecondaryContainer = SobreContenedorSecundario,
    tertiary = Terciario,
    onTertiary = Color.White,
    background = Fondo,
    onBackground = Texto,
    surface = Superficie,
    onSurface = Texto
)

@Composable
fun OurSourTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaOurSour,
        typography = Typography,
        content = content
    )
}