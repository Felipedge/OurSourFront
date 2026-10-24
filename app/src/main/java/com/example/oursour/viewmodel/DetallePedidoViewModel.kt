package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel

class DetallePedidoViewModel : ViewModel(){
    // aqui van los datos fijos de la prueba que tendremos mas adelante
    val pedido = "Pedido N 1°"
    val estado = "Estado: EN_PREPARACION"
    val direccion = "Direccion: Lord crochrane 184, santiago centro"
    val subtotal = 19990
    val costoDespacho = 3990
    val montoTotal = 23980
}