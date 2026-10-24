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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.PagoViewModel

@Composable
fun PagoScreen(
    onPagoRealizado: () -> Unit,
    onVolver: () -> Unit,
    pagoViewModel: PagoViewModel = viewModel()
) {
    PantallaConBarra(titulo = "Pago", onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = pagoViewModel.pedido, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Monto a pagar: ${formatearPesos(pagoViewModel.monto)}",
                style = MaterialTheme.typography.bodyLarge
            )

            // aqui tenemos  Caja de texto del
            OutlinedTextField(
                value = pagoViewModel.metodo,
                onValueChange = { pagoViewModel.cambiarMetodo(it) },
                label = { Text("metodo de pago") },
                modifier = Modifier.fillMaxWidth()
            )

            //aqui tenemos los  Mensajes de error
            if (pagoViewModel.textoVisible) {
                Text(
                    text = "ingrese un metodo de pago",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            //aqui tenemos los  Botón para pagar
            Button(
                onClick = {
                    pagoViewModel.textoVisible = false
                    val validador = pagoViewModel.pagar()
                    if (validador) {
                        onPagoRealizado()
                    } else {
                        pagoViewModel.textoVisible = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Pagar")
            }
        }
    }
}