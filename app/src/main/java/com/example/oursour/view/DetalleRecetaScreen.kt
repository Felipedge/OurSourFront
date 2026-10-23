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
import com.example.oursour.viewmodel.DetalleRecetaViewModel

@Composable
fun DetalleRecetaScreen(detalleRecetaViewModel: DetalleRecetaViewModel = viewModel()){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //aqui va el titulo de la receta
        Text(
            text = detalleRecetaViewModel.titulo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp, bottom = 12.dp)

        )
        // datos de la receta
        Text(text = detalleRecetaViewModel.tipo, fontSize = 20.sp)
        Text(text = detalleRecetaViewModel.ingredientes)
        Text(text = detalleRecetaViewModel.pasos)

        // aca es el espacio entre los datos y el bottom
        Button(
            onClick = {

                // aqui va la navegacion mi bro

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Volver a recetas")
        }
    }
}