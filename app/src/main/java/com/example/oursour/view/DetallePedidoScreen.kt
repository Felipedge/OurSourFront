package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.DetallePedidoViewModel

@Composable
fun DetallePedidoScreen(
    onVolver: () -> Unit,
    detallePedidoViewModel: DetallePedidoViewModel = viewModel()
) {
    PantallaConBarra(titulo = "Detalle del pedido", onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Datos del pedido que vienen del ViewModel
            Text(text = detallePedidoViewModel.pedido, style = MaterialTheme.typography.titleMedium)
            Text(text = detallePedidoViewModel.estado, style = MaterialTheme.typography.bodyLarge)
            Text(text = detallePedidoViewModel.direccion, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Subtotal: ${formatearPesos(detallePedidoViewModel.subtotal)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Despacho: ${formatearPesos(detallePedidoViewModel.costoDespacho)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Total: ${formatearPesos(detallePedidoViewModel.montoTotal)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}