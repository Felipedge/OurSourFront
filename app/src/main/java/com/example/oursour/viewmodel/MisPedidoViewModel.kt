package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.PedidoLocal
import com.example.oursour.model.PedidosLocal

class MisPedidoViewModel: ViewModel(){
    val titulo = "Mis pedidos"

    val pedidos: List<PedidoLocal>
        get() = PedidosLocal.pedidos
    fun elegirPedido(numero: Int) {
        PedidosLocal.numeroElegido = numero
    }
}