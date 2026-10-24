package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.CarroLocal
import com.example.oursour.model.ItemCarro

class CarritoViewModel : ViewModel() {
    val titulo = "Mi carrito"

    val items: List<ItemCarro>
        get() = CarroLocal.items

    val resumen: String
        get() = "Cantidad de productos: ${CarroLocal.cantidadTotal}"

    val subtotal: Double
        get() = CarroLocal.subtotal

    val costoDespacho: Double
        get() = CarroLocal.costoDespacho

    val total: Double
        get() = CarroLocal.total

    fun cambiarCantidad(idProducto: Long, nuevaCantidad: Int) {
        CarroLocal.cambiarCantidad(idProducto, nuevaCantidad)
    }

    fun eliminar(idProducto: Long) {
        CarroLocal.eliminar(idProducto)
    }
}