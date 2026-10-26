package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.ItemCarro
import com.example.oursour.model.PedidoLocal
import com.example.oursour.model.PedidosLocal

class DetallePedidoViewModel : ViewModel(){
    private val elegido: PedidoLocal? =
        PedidosLocal.pedidos.firstOrNull { it.numero == PedidosLocal.numeroElegido }

    val pedido: String =
        if (elegido == null) "Pedido no encontrado" else "Pedido N ${elegido.numero}°"
    val estado: String = elegido?.estado ?: ""
    val direccion: String = elegido?.direccion ?: ""
    val items: List<ItemCarro> = elegido?.items ?: listOf()
    val subtotal: Double = elegido?.subtotal ?: 0.0
    val costoDespacho: Double = elegido?.costoDespacho ?: 0.0
    val montoTotal: Double = elegido?.total ?: 0.0
}