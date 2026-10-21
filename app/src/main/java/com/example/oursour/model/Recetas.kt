package com.example.oursour.model

data class Ingredientes(
    val idIngredientes: Long,
    val nombre: String,
    val categoria: String,
    val idProducto: Long?

)

data class DetalleReceta(
    val idDetalle : Long,
    val idIngredientes: Long,
    val dosificacion: String
)

data class Recetas(
    val idRecetas: Long,
    val titulo : String,
    val tipo: String,
    val pasosPreparacion: String,
    val idProducto: Long,
    val detalles : List<DetalleReceta>
)
