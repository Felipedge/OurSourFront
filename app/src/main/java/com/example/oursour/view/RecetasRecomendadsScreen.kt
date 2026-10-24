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
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.RecetasRecomendadasViewModel

@Composable
fun RecetasRecomendadasScreen(recetasRecomendadasViewModel: RecetasRecomendadasViewModel = viewModel()){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ){
        //titulo
        Text(
            text = recetasRecomendadasViewModel.titulo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp, bottom = 12.dp)

        )
        //recetas recomendadas
        Text(text = recetasRecomendadasViewModel.receta1)
        Text(text = recetasRecomendadasViewModel.receta2)
        Text(text = recetasRecomendadasViewModel.receta3)

        // bottom para ver el datalle de la receta
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
            Text(text = "Ver detalle de la receta")
        }
    }
}