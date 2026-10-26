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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.DetalleRecetaViewModel

@Composable
fun DetalleRecetaScreen(
    onVolver: () -> Unit,
    onIrAlCarroClick: () -> Unit = {},
    detalleRecetaViewModel: DetalleRecetaViewModel = viewModel()
) {
    PantallaConBarra(titulo = detalleRecetaViewModel.titulo, onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Etiqueta(texto = textoTipoReceta(detalleRecetaViewModel.tipo))


            TituloSeccion("Ingredientes")
            detalleRecetaViewModel.ingredientes.forEach { ingrediente ->
                val marca = if (ingrediente in detalleRecetaViewModel.faltantes) "✗ " else "✓ "
                Text(text = marca + ingrediente.nombre, style = MaterialTheme.typography.bodyLarge)
            }


            TituloSeccion("Te faltan")
            if (detalleRecetaViewModel.faltantes.isEmpty()) {
                Text(text = "Tienes todos los ingredientes", style = MaterialTheme.typography.bodyLarge)
            } else {
                detalleRecetaViewModel.faltantes.forEach { faltante ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "• " + faltante.nombre, style = MaterialTheme.typography.bodyLarge)
                        // el botón solo aparece si el ingrediente se vende en el catálogo
                        if (detalleRecetaViewModel.seVende(faltante)) {
                            OutlinedButton(
                                onClick = { detalleRecetaViewModel.agregarAlCarro(faltante) },
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Text("Agregar al carro")
                            }
                        } else {
                            Text(text = "No se vende en el catálogo", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }


            if (detalleRecetaViewModel.mensaje != "") {
                Text(text = detalleRecetaViewModel.mensaje, style = MaterialTheme.typography.bodyMedium)
            }

            TituloSeccion("Preparación")
            Text(text = detalleRecetaViewModel.pasos, style = MaterialTheme.typography.bodyLarge)


            Button(
                onClick = onIrAlCarroClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Ir al carro")
            }
        }
    }
}