package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.PedidosLocal

class ResultadoPagoViewModel: ViewModel(){
    // el resultado viene del último intento de pago
    val esError: Boolean
        get() = PedidosLocal.ultimoResultado == "RECHAZADO"

    val estado: String
        get() = PedidosLocal.ultimoResultado

    val mensaje: String
        get() = if (esError) "Tu pago fue rechazado, prueba con otro metodo de pago" else "Tu pago fue aprobado, tu pedido esta confirmado"

    val pedido: String
        get() = if (esError) "No se creo ningun pedido, tu carro sigue guardado" else "Pedido N ${PedidosLocal.ultimoNumero}°"
}