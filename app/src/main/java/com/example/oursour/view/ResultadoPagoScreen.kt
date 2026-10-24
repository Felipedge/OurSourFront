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
import com.example.oursour.viewmodel.ResultadoPagoViewModel

@Composable
fun ResultadoPagoScreen(
    onVolverAlInicioClick: () -> Unit,
    resultadoPagoViewModel: ResultadoPagoViewModel = viewModel()
) {
    PantallaConBarra(titulo = "Resultado del pago") { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = resultadoPagoViewModel.estado,
                style = MaterialTheme.typography.headlineMedium,
                color = if (resultadoPagoViewModel.esError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
            )
            Text(text = resultadoPagoViewModel.mensaje, style = MaterialTheme.typography.bodyLarge)
            Text(text = resultadoPagoViewModel.pedido, style = MaterialTheme.typography.bodyMedium)

            //aqui tenemos los Botónes para volver al inicio
            Button(
                onClick = onVolverAlInicioClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Volver al inicio")
            }
        }
    }
}