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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //aqui tenemos el SProductos del carrito
            Text(text = carritoViewModel.producto1, style = MaterialTheme.typography.bodyLarge)
            Text(text = carritoViewModel.producto2, style = MaterialTheme.typography.bodyLarge)
            Text(text = carritoViewModel.producto3, style = MaterialTheme.typography.bodyLarge)

            // Cantidad de productos
            Text(text = carritoViewModel.resumen, style = MaterialTheme.typography.titleMedium)

            //aqui tenemos el  Botón principal
            Button(
                onClick = onContinuarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Continuar a despacho")
            }

            // aqui tenemos el  Botón secundario
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