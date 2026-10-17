package com.example.oursour.model

data class ResumenMetricas(
    val totalBusquedas: Long,
    val totalConsultasReceta: Long,
    val totalPedidosPagados: Long,
    val ventasTotales: Double
)



data class Conteo(
    val id: Long,
    val nombre: String,
    val cantidad: Long
)


data class BusquedasDia(
    val fecha: String,
    val busquedas: Long
)
data class Tendencia(
    val desde: String,
    val hasta: String,
    val totalBusquedas: Long,
    val busquedasPorDia: List<BusquedasDia>,
    val ingredientesDelPeriodo: List<Conteo>
)


data class Combinacion(
    val idIngrediente1: Long,
    val nombre1: String,
    val idIngrediente2: Long,
    val nombre2: String,
    val cantidad: Long
)