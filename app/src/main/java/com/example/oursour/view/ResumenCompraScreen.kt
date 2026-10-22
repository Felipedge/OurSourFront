package com.example.oursour.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oursour.viewmodel.ResumenCompraViewModel

@Composable
fun ResumenCompraScreen(resumenCompraViewModel: ResumenCompraViewModel = viewModel()){
    //aqui va el column
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // aqui va el titulo de la pantalla
        Text(
            text = "Resumen de compra",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp,bottom = 12.dp)
        )
        // datos de pedido que vienen de viewmodel
        Text(text = resumenCompraViewModel.pedido)
        Text(text = resumenCompraViewModel.subtotal, fontSize = 20.sp)
        Text(text = resumenCompraViewModel.costoDespacho, fontSize = 20.sp)
        Text(
            text = resumenCompraViewModel.montoTotal,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        // aca va el espacio entre los datos y el botton
        Spacer(modifier = Modifier.height(15.dp))

        // bottoo para continuar al pago
        Button(
            onClick = {
                // aqui ira la navegacion
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Ir a pagar")

        }
    }
}