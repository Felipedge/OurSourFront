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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //aqui tenemos los Pedidos del usuario
            Text(
                text = "${misPedidosViewModel.pedido1} - Total: ${formatearPesos(misPedidosViewModel.total1)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${misPedidosViewModel.pedido2} - Total: ${formatearPesos(misPedidosViewModel.total2)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${misPedidosViewModel.pedido3} - Total: ${formatearPesos(misPedidosViewModel.total3)}",
                style = MaterialTheme.typography.bodyLarge
            )

            //aqui tenemos los Botónes para ver el detalle
            Button(
                onClick = onVerDetalleClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Ver detalle del pedido")
            }
        }
    }
}
