package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.IngredienteFrecuenteViewModel

@Composable
fun IngredienteFrecuenteScreen(
    onVerRecetasClick: () -> Unit = {},
    onVolver: () -> Unit,
    ingredienteFrecuenteViewModel: IngredienteFrecuenteViewModel = viewModel()
) {
    PantallaConBarra(
        titulo = ingredienteFrecuenteViewModel.titulo,
        onVolver = onVolver
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // aqui tenemos la lista de los ingredientes mas buscados
            TituloSeccion("Tus ingredientes frecuentes")
            if (ingredienteFrecuenteViewModel.listaFrecuentes.isEmpty()) {
                Text(
                    text = "Aún no tienes ingredientes frecuentes",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                ingredienteFrecuenteViewModel.listaFrecuentes.forEach { ingrediente ->
                    Text(
                        text = "• " + ingrediente.nombre + " (" + ingredienteFrecuenteViewModel.veces(ingrediente.id) + " veces)",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}