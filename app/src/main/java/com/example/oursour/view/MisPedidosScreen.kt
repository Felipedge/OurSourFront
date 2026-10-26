package com.example.oursour.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.MisPedidoViewModel

@Composable
fun MisPedidosScreen(
    onVerDetalleClick: () -> Unit,
    onVolver: () -> Unit,
    misPedidosViewModel: MisPedidoViewModel = viewModel()
) {
    PantallaConBarra(titulo = misPedidosViewModel.titulo, onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (misPedidosViewModel.pedidos.isEmpty()) {
                Text(text = "Aún no tienes pedidos", style = MaterialTheme.typography.bodyLarge)
            }


            misPedidosViewModel.pedidos.forEach { pedido ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            misPedidosViewModel.elegirPedido(pedido.numero)
                            onVerDetalleClick()
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Pedido N ${pedido.numero}°", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Estado: ${textoEstadoPedido(pedido.estado)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Total: ${formatearPesos(pedido.total)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
