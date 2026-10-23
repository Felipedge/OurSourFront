package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore

class ResumenCompraViewModel : ViewModel(){
    // esto son lo datos fijo de prueba
    val pedido = "Pedido N 1°"
    val subtotal = "Subtotal: $19990"
    val costoDespacho = "Despacho: $3990"
    val montoTotal = "Total: $23980"
}