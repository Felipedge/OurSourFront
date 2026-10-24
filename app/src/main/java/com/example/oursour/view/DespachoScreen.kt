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
import com.example.oursour.viewmodel.DespachoViewModel

@Composable
fun DespachoScreen(
    onDireccionGuardada: () -> Unit,
    onVolver: () -> Unit,
    despachoViewModel: DespachoViewModel = viewModel()
) {
    PantallaConBarra(titulo = "Despacho", onVolver = onVolver) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //aqui tenemos la  Caja de texto de calle
            OutlinedTextField(
                value = despachoViewModel.calle,
                onValueChange = { despachoViewModel.cambiarCalle(it) },
                label = { Text("Calle") },
                modifier = Modifier.fillMaxWidth()
            )

            //aqui tenemos la  Caja de texto de número
            OutlinedTextField(
                value = despachoViewModel.numero,
                onValueChange = { despachoViewModel.cambiarNumero(it) },
                label = { Text("Numero") },
                modifier = Modifier.fillMaxWidth()
            )

            //aqui tenemos la  Caja de texto de comuna
            OutlinedTextField(
                value = despachoViewModel.comuna,
                onValueChange = { despachoViewModel.cambiarComuna(it) },
                label = { Text("Comuna") },
                modifier = Modifier.fillMaxWidth()
            )

            //aqui tenemos la  Caja de texto de ciudad
            OutlinedTextField(
                value = despachoViewModel.ciudad,
                onValueChange = { despachoViewModel.cambiarCiudad(it) },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth()
            )

            //aqui tenemos la  Caja de texto de referencia
            OutlinedTextField(
                value = despachoViewModel.referencia,
                onValueChange = { despachoViewModel.cambiarReferencia(it) },
                label = { Text("Referencia") },
                modifier = Modifier.fillMaxWidth()
            )

            //aqui tenemos el  Mensaje de error
            if (despachoViewModel.textoVisible) {
                Text(
                    text = "Completa la calle, numero, comuna, ciudad",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            //aqui tenemos el  Botón para guardar la dirección
            Button(
                onClick = {
                    despachoViewModel.textoVisible = false
                    val validador = despachoViewModel.guardar()
                    if (validador) {
                        onDireccionGuardada()
                    } else {
                        despachoViewModel.textoVisible = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Guardar dirección")
            }
        }
    }
}