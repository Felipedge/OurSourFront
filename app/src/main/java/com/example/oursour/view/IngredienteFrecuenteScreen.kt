package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.IngredienteFrecuenteViewModel

@Composable
fun IngredienteFrecuenteScreen(
    onVerRecetasClick: () -> Unit,
    onVolver: () -> Unit,
    ingredienteFrecuenteViewModel: IngredienteFrecuenteViewModel = viewModel()
) {
    PantallaConBarra(titulo = ingredienteFrecuenteViewModel.titulo, onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //aqui tenemos los  Ingredientes más usados
            Text(text = ingredienteFrecuenteViewModel.ingrediente1, style = MaterialTheme.typography.bodyLarge)
            Text(text = ingredienteFrecuenteViewModel.ingrediente2, style = MaterialTheme.typography.bodyLarge)
            Text(text = ingredienteFrecuenteViewModel.ingrediente3, style = MaterialTheme.typography.bodyLarge)

            // aqui tenemos los  Botónes para ver recetas
            Button(
                onClick = onVerRecetasClick,
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