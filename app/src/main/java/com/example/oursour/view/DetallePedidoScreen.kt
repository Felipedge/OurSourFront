package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.oursour.viewmodel.DetallePedidoViewModel

@Composable
fun DetallePedidoScreen(
    onVolver: () -> Unit,
    detallePedidoViewModel: DetallePedidoViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Detalle del pedido",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp, bottom = 12.dp)
        )

        // Datos del pedido que vienen del ViewModel
        Text(text = detallePedidoViewModel.pedido, fontSize = 20.sp)
        Text(text = detallePedidoViewModel.estado)
        Text(text = detallePedidoViewModel.direccion)
        Text(text = detallePedidoViewModel.subtotal)
        Text(text = detallePedidoViewModel.costoDespacho)


        Text(
            text = detallePedidoViewModel.montoTotal,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Espacio entre los datos y el botón
        Spacer(modifier = Modifier.height(15.dp))

        // Botón para volver
        Button(
            onClick = onVolver,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Volver")
        }
    }
}