package com.example.oursour.model



data class Pago(
    val idPago: Long,
    val idPedido: Long,
    val idUsuario: Long,
    val monto: Double,
    val metodo: String,
    val estado: String,
    val fechaPago: String
)