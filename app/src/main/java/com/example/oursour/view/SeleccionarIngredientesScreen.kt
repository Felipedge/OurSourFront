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
import com.example.oursour.viewmodel.SeleccionarIngredientesViewModel

@Composable
fun SeleccionarIngredientesScreen(
    onVerRecetasClick: () -> Unit,
    onVolver: () -> Unit,
    seleccionarIngredientesViewModel: SeleccionarIngredientesViewModel = viewModel()
) {
    PantallaConBarra(titulo = "Selecciona ingredientes", onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Marca los ingredientes que tienes",
                style = MaterialTheme.typography.titleMedium
            )

            // aqui tenemos  Un ingrediente por fila, con su respectiva  casilla
            RecetaRepositoryLocal.ingredientes.forEach { ingrediente ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = ingrediente.id in seleccionarIngredientesViewModel.seleccionados,
                        onCheckedChange = { seleccionarIngredientesViewModel.alternar(ingrediente.id) }
                    )
                    Text(text = ingrediente.nombre)
                }
            }

            Button(
                onClick = {
                    seleccionarIngredientesViewModel.guardarSeleccion()
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
}