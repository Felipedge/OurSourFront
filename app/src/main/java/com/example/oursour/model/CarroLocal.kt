package com.example.oursour.model



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ItemCarro(
    val producto: Producto,
    val cantidad: Int
)

object CarroLocal {
    var items by mutableStateOf(listOf<ItemCarro>())

    fun agregar(producto: Producto) {
        val existente = items.firstOrNull { it.producto.idProducto == producto.idProducto }
        if (existente == null) {
            items = items + ItemCarro(producto, 1)
        } else {
            cambiarCantidad(producto.idProducto, existente.cantidad + 1)
        }
    }

    fun cambiarCantidad(idProducto: Long, nuevaCantidad: Int) {
        items = items.map {
            if (it.producto.idProducto == idProducto) it.copy(cantidad = nuevaCantidad) else it
        }
    }

    fun eliminar(idProducto: Long) {
        items = items.filter { it.producto.idProducto != idProducto }
    }

    fun vaciar() {
        items = listOf()
    }

    val cantidadTotal: Int
        get() = items.sumOf { it.cantidad }

    val subtotal: Double
        get() = items.sumOf { it.producto.precioUnitario * it.cantidad }

    //aqui tenemos el  Despacho: gratis desde $50.000, si no $3.990 (estrategia propia, el caso lo permite)
    val costoDespacho: Double
        get() = if (items.isEmpty() || subtotal >= 50000.0) 0.0 else 3990.0

    val total: Double
        get() = subtotal + costoDespacho
}