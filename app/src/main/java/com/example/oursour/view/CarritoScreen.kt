package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.CarritoViewModel

@Composable
fun CarritoScreen(
    onContinuarClick: () -> Unit,
    onSeguirComprandoClick: () -> Unit,
    onVolver: () -> Unit,
    carritoViewModel: CarritoViewModel = viewModel()
) {
    PantallaConBarra(titulo = carritoViewModel.titulo, onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (carritoViewModel.items.isEmpty()) {
                Text(text = "Tu carrito está vacío", style = MaterialTheme.typography.bodyLarge)
            }


            carritoViewModel.items.forEach { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = item.producto.nombreComercial, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "${formatearPesos(item.producto.precioUnitario)} c/u",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SelectorCantidad(
                                cantidad = item.cantidad,
                                onMenos = { carritoViewModel.cambiarCantidad(item.producto.idProducto, item.cantidad - 1) },
                                onMas = { carritoViewModel.cambiarCantidad(item.producto.idProducto, item.cantidad + 1) },
                                maximo = item.producto.stockSimulado
                            )
                            OutlinedButton(
                                onClick = { carritoViewModel.eliminar(item.producto.idProducto) },
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("Eliminar")
                            }
                        }
                        Text(
                            text = "Subtotal: ${formatearPesos(item.producto.precioUnitario * item.cantidad)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }


            Text(text = carritoViewModel.resumen, style = MaterialTheme.typography.titleMedium)


            Text(text = "Subtotal: ${formatearPesos(carritoViewModel.subtotal)}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Despacho: ${formatearPesos(carritoViewModel.costoDespacho)}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Total: ${formatearPesos(carritoViewModel.total)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )


            if (carritoViewModel.items.isNotEmpty()) {
                Button(
                    onClick = onContinuarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Continuar a despacho")
                }
            }


            OutlinedButton(
                onClick = onSeguirComprandoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Seguir comprando")
            }
        }
    }
}