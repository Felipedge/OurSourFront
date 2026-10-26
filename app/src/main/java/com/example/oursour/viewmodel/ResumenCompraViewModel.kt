package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.CarroLocal
import com.example.oursour.model.ItemCarro

class ResumenCompraViewModel : ViewModel() {
    val pedido = "Pedido N 1°"
    val items: List<ItemCarro>
        get() = CarroLocal.items
    val subtotal: Double
        get() = CarroLocal.subtotal
    val costoDespacho: Double
        get() = CarroLocal.costoDespacho
    val montoTotal: Double
        get() = CarroLocal.total
}