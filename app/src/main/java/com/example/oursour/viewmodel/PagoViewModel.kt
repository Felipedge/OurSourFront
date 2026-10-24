package com.example.oursour.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.oursour.model.CarroLocal
import com.example.oursour.model.PedidosLocal

class PagoViewModel: ViewModel() {
    // datos que vienen del carro
    val pedido: String
        get() = "Pedido N ${PedidosLocal.pedidos.size + 1}°"
    val monto: Double
        get() = CarroLocal.total

    // opciones de pago y cuotas disponibles
    val opciones = listOf("Tarjeta de débito", "Tarjeta de crédito", "Tarjeta de prueba (rechazada)")
    val cuotasDisponibles = listOf(1, 3, 6, 12)

    var metodo by mutableStateOf("")
    var cuotas by mutableStateOf(1)
    var textoVisible by mutableStateOf(false)

    // las cuotas solo se eligen con tarjeta de crédito
    val esCredito: Boolean
        get() = metodo == "Tarjeta de crédito"

    fun cambiarMetodo(nuevoMetodo: String) {
        metodo = nuevoMetodo
    }

    fun cambiarCuotas(nuevasCuotas: Int) {
        cuotas = nuevasCuotas
    }

    fun pagar(): Boolean {
        var validador =  true
        if (metodo == "") {
            validador = false
        }
        return validador
    }

    fun confirmarPago() {
        PedidosLocal.pagar(metodo)
    }
}
