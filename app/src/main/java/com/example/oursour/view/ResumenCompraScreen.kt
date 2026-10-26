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
import com.example.oursour.viewmodel.ResumenCompraViewModel

@Composable
fun ResumenCompraScreen(
    onIrAPagarClick: () -> Unit,
    onVolver: () -> Unit,
    resumenCompraViewModel: ResumenCompraViewModel = viewModel()
) {
    PantallaConBarra(titulo = "Resumen de compra", onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(text = resumenCompraViewModel.pedido, style = MaterialTheme.typography.titleMedium)


            resumenCompraViewModel.items.forEach { item ->
                Text(
                    text = "${item.producto.nombreComercial} x${item.cantidad} - ${formatearPesos(item.producto.precioUnitario * item.cantidad)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(text = "Subtotal: ${formatearPesos(resumenCompraViewModel.subtotal)}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Despacho: ${formatearPesos(resumenCompraViewModel.costoDespacho)}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Total: ${formatearPesos(resumenCompraViewModel.montoTotal)}", style = MaterialTheme.typography.titleMedium)


            Button(
                onClick = onIrAPagarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Ir a pagar")
            }
        }
    }
}
