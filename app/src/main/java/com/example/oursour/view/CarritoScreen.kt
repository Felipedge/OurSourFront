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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.CarritoViewModel

@Composable
fun CarritoScreen(carritoViewModel: CarritoViewModel = viewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // aqui va el titulo
        Text(
            text = carritoViewModel.titulo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp, bottom = 12.dp)

        )
        //productos del carrito
        Text(text = carritoViewModel.producto1)
        Text(text = carritoViewModel.producto2)
        Text(text = carritoViewModel.producto3)

        Text(
            text = carritoViewModel.resumen,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 60.dp, bottom = 12.dp)

        )
        Button(
            onClick = {
                // Aquí irá la navegación a DespachoScreen
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Continuar a despacho")
        }

        Button(
            onClick = {
                //aqui ira la navegacion
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Seguir comprando")
        }

    }
}