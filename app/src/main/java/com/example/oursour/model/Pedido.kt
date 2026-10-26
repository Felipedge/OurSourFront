package com.example.oursour.model

data class DireccionDespacho(
    val idDireccion : Long,
    val idUsuario : Long,
    val calle: String,
    val numero: String,
    val comuna: String,
    val ciudad: String,
    val referencia: String?
)

data class CarritoProducto(
    val idDetalle: Long,
    val idProducto: Long,
    val cantidad : Int
)

data class Carrito(
    val idCarrito: Long,
    val idUsuario: Long,
    val fechaCreacion: String,
    val productos:  List<CarritoProducto>
)

data class Pedido(
    val idPedido: Long,
    val idUsuario: Long,
    val idDireccion: Long,
    val subtotal: Double,
    val costoDespacho: Double,
    val montoTotal: Double,
    val estado : String,
    val fechaCreacion: String,
    val productos : List<PedidoProducto>

)

data class PedidoProducto(
    val idDetalle: Long,
    val idProducto: Long,
    val cantidad: Int,
    val precioUnitario: Double
)