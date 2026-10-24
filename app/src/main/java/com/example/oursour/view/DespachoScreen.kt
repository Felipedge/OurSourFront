package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.DespachoViewModel

@Composable
fun DespachoScreen(
    onDireccionGuardada: () -> Unit,
    onVolver: () -> Unit,
    despachoViewModel: DespachoViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Despacho",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Caja de texto de calle
        OutlinedTextField(
            value = despachoViewModel.calle,
            onValueChange = { despachoViewModel.cambiarCalle(it) },
            label = { Text("Calle") },
            modifier = Modifier.fillMaxWidth()
        )

        // Caja de texto de número
        OutlinedTextField(
            value = despachoViewModel.numero,
            onValueChange = { despachoViewModel.cambiarNumero(it) },
            label = { Text("Numero") },
            modifier = Modifier.fillMaxWidth()
        )

        // Caja de texto de comuna
        OutlinedTextField(
            value = despachoViewModel.comuna,
            onValueChange = { despachoViewModel.cambiarComuna(it) },
            label = { Text("Comuna") },
            modifier = Modifier.fillMaxWidth()
        )

        // Caja de texto de ciudad
        OutlinedTextField(
            value = despachoViewModel.ciudad,
            onValueChange = { despachoViewModel.cambiarCiudad(it) },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
        )

        // Caja de texto de referencia
        OutlinedTextField(
            value = despachoViewModel.referencia,
            onValueChange = { despachoViewModel.cambiarReferencia(it) },
            label = { Text("Referencia") },
            modifier = Modifier.fillMaxWidth()
        )

        // Mensaje de error
        if (despachoViewModel.textoVisible) {
            Text(text = "Completa la calle, numero, comuna, ciudad", color = Color.Red)
        }

        // Espacio entre las cajas y los botones
        Spacer(modifier = Modifier.height(15.dp))

        // Botón para guardar la dirección
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

        // Botón para volver
        Button(
            onClick = onVolver,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Volver")
        }
    }
}