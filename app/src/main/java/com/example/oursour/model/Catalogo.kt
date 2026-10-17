package com.example.oursour.model

data class Producto(
    val idProducto: Long,
    val nombreComercial: String,
    val descripcion: String?,
    val precioUnitario: Double,
    val stockSimulado: Int,
    val activo: Boolean
) {
    val hayStock: Boolean get() = stockSimulado > 0
}

data class ProductoRequest(
    val nombreComercial: String,
    val descripcion: String?,
    val precioUnitario: Double,
    val stockSimulado: Int
)