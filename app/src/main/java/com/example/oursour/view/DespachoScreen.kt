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
import androidx.room.util.TableInfo
import com.example.oursour.viewmodel.DespachoViewModel


@Composable
fun DespachoScreen(despachoViewModel: DespachoViewModel = viewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()// con esto la columna ocupa toda la pantalla
            .padding(16.dp) // aqui ocupamos margen de 16 para que no quede pegada a los bordes

    ) {
        // con el text le damos el titulo a ala pantalla
        Text(
            text = "Despacho",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // 1 CALLE CAJA DE TEXTO
        OutlinedTextField(
            value = despachoViewModel.calle,
            onValueChange = { despachoViewModel.cambiarCalle(it) },
            label = { Text("Calle") },
            modifier = Modifier.fillMaxWidth()
        )
        // 2 es la caja de texto de numero que es lo misma logica
        OutlinedTextField(
            value = despachoViewModel.numero,
            onValueChange = { despachoViewModel.cambiarNumero(it) },
            label = { Text("Numero") },
            modifier = Modifier.fillMaxWidth()

        )
        // 3caja de texto de comuna
        OutlinedTextField(
            value = despachoViewModel.comuna,
            onValueChange = { despachoViewModel.cambiarComuna(it) },
            label = { Text("Comuna") },
            modifier = Modifier.fillMaxWidth()
        )

        // 4 caja de texto de ciudad
        OutlinedTextField(
            value = despachoViewModel.ciudad,
            onValueChange = { despachoViewModel.cambiarCiudad(it) },
            label = { Text("ciudad") },
            modifier = Modifier.fillMaxWidth()
        )


        // 5 caja de texto de referencias
        OutlinedTextField(
            value = despachoViewModel.referencia,
            onValueChange = { despachoViewModel.cambiarReferencia(it) },
            label = { Text("Referencia") },
            modifier = Modifier.fillMaxWidth()
        )

        // mensaje de error
        if (despachoViewModel.textoVisible) {
            Text(text = "Completa la calle, numero, comuna, ciudad", color = Color.Red)
        }
        // espacio vacio de 15 de alto entre las cajas y el bottom
        Spacer(modifier = Modifier.height(15.dp))


        Button(
            onClick = {
                despachoViewModel.textoVisible = false
                val validador = despachoViewModel.guardar()
                if(validador){
                }else {
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