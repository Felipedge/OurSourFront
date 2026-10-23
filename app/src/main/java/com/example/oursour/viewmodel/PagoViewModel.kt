package com.example.oursour.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class PagoViewModel: ViewModel() {
    // datos de prueba
    val pedido = "Pedido N 1°"
    val monto = "Monto a pagar: $23980"

    var metodo by mutableStateOf("")
    var textoVisible by mutableStateOf(false)

    fun cambiarMetodo(nuevoMetodo: String) {
        metodo = nuevoMetodo
    }

    fun pagar(): Boolean {
        var validador =  true
        if (metodo == "") {
            validador = false
        }
        return validador
    }
}
