package com.example.oursour.view

import android.widget.Space
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
import com.example.oursour.viewmodel.PagoViewModel

@Composable
fun PagoScreen(pagoViewModel: PagoViewModel = viewModel()){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Text(
            text = "Pago",
            fontSize = 32.sp,
            fontWeight =  FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(text = pagoViewModel.pedido)
        Text(text = pagoViewModel.monto, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = pagoViewModel.metodo,
            onValueChange = {pagoViewModel.cambiarMetodo(it)},
            label = {Text("metodo de pago") },
            modifier = Modifier.fillMaxWidth()
        )

        if(pagoViewModel.textoVisible){
            Text(text = "ingrese un metodo de pago", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                pagoViewModel.textoVisible  = false
                val validador = pagoViewModel.pagar()
                if(validador) {
                    //aqui ira la naevagacion mas adelante
                }else{
                    pagoViewModel.textoVisible = true
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ){
            Text(text = "Pagar")
        }
    }
}