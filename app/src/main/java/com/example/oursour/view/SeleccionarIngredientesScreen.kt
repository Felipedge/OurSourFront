package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.SeleccionarIngredientesViewModel

@Composable
fun SeleccionarIngredientesViewModel(seleccionarIngredientesViewModel: SeleccionarIngredientesViewModel = viewModel()){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //titulo
        Text(
            text = seleccionarIngredientesViewModel .titulo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp, bottom = 12.dp)
        )
        //ingredientes disponibles
        Text(text = seleccionarIngredientesViewModel.ingrediente1)
        Text(text = seleccionarIngredientesViewModel.ingrediente2)
        Text(text = seleccionarIngredientesViewModel.ingredientes3)
        Text(text = seleccionarIngredientesViewModel.ingrediente4)


        // Botón para ver las recetas
        Button(
            onClick = {
                // Aquí irá la vegacion
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Ver recetas")
        }
        Button(
            onClick = {
                //aqui va la navegacion
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Volver")
        }

    }
}