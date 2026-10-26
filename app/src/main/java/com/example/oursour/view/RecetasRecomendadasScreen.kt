package com.example.oursour.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.RecetasRecomendadasViewModel

@Composable
fun RecetasRecomendadasScreen(
    onVerDetalleClick: () -> Unit,
    onVolver: () -> Unit,
    recetasRecomendadasViewModel: RecetasRecomendadasViewModel = viewModel()
) {
    PantallaConBarra(titulo = "Recetas recomendadas", onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (recetasRecomendadasViewModel.recomendadas.isEmpty()) {
                Text(
                    text = "No hay recetas con los ingredientes elegidos",
                    style = MaterialTheme.typography.bodyLarge
                )
            }


            recetasRecomendadasViewModel.recomendadas.forEach { recomendada ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            recetasRecomendadasViewModel.elegirReceta(recomendada.receta.id)
                            onVerDetalleClick()
                        },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = recomendada.receta.titulo,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Coincidencia: ${recomendada.porcentaje}%",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (recomendada.faltantes.isNotEmpty()) {
                            Text(
                                text = "Te falta: " + recomendada.faltantes.joinToString { it.nombre },
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}