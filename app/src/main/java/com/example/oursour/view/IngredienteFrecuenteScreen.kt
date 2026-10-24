package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.model.RecetaRepositoryLocal
import com.example.oursour.viewmodel.IngredienteFrecuenteViewModel

@Composable
fun IngredienteFrecuenteScreen(
    onVerRecetasClick: () -> Unit,
    onVolver: () -> Unit,
    ingredienteFrecuenteViewModel: IngredienteFrecuenteViewModel = viewModel()
) {
    PantallaConBarra(
        titulo = ingredienteFrecuenteViewModel.titulo,
        onVolver = onVolver,
        // aqui tenemos los  Botónes para ver recetas, fijo abajo y siempre visible
        barraInferior = {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        ingredienteFrecuenteViewModel.guardarSeleccion()
                        onVerRecetasClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Ver recetas")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Marca los ingredientes que usas siempre",
                style = MaterialTheme.typography.titleMedium
            )

            //aqui tenemos los  Ingredientes, uno por fila con su casilla
            RecetaRepositoryLocal.ingredientes.forEach { ingrediente ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = ingrediente.id in ingredienteFrecuenteViewModel.frecuentes,
                        onCheckedChange = { ingredienteFrecuenteViewModel.alternar(ingrediente.id) }
                    )
                    Text(text = ingrediente.nombre, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}