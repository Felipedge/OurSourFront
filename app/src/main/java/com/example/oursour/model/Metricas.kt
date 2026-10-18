package com.example.oursour.model

import kotlinx.coroutines.delay
import java.time.LocalDate

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
data class Combinacion(
    val idIngrediente1: Long,
    val nombre1: String,
    val idIngrediente2: Long,
    val nombre2: String,
    val cantidad: Long
)
data class Tendencia(
    val desde: String,
    val hasta: String,
    val totalBusquedas: Long,
    val busquedasPorDia: List<BusquedasDia>,
    val ingredientesDelPeriodo: List<Conteo>
)
data class BusquedasDia(
    val fecha: String,
    val busquedas: Long
)

data class Referencia(
    val id: Long,
    val nombre: String
)


data class ProductoVendido(
    val id: Long,
    val nombre: String,
    val cantidad: Int
)

interface MetricasRepository {
    suspend fun resumen(): Result<ResumenMetricas>
    suspend fun ingredientesMasBuscados(limite: Int = 10): Result<List<Conteo>>
    suspend fun recetasMasConsultadas(limite: Int = 10): Result<List<Conteo>>
    suspend fun productosMasVendidos(limite: Int = 10): Result<List<Conteo>>
    suspend fun combinacionesFrecuentes(limite: Int = 10): Result<List<Combinacion>>
    suspend fun tendencias(dias: Int = 30): Result<Tendencia>

    fun registrarBusqueda(ingredientes: List<Referencia>)
    fun registrarConsulta(receta: Referencia)
    fun registrarVenta(productos: List<ProductoVendido>, total: Double)
}

object MetricasRepositoryLocal : MetricasRepository {

    private data class Busqueda(val fecha: LocalDate, val ingredientes: List<Referencia>)
    private data class Venta(val productos: List<ProductoVendido>, val total: Double)

    private val busquedas = mutableListOf<Busqueda>()
    private val consultas = mutableListOf<Referencia>()
    private val ventas = mutableListOf<Venta>()


    override fun registrarBusqueda(ingredientes: List<Referencia>) {
        if (ingredientes.isNotEmpty()) {
            busquedas.add(Busqueda(LocalDate.now(), ingredientes.distinctBy { it.id }))
        }
    }

    override fun registrarConsulta(receta: Referencia) {
        consultas.add(receta)
    }

    override fun registrarVenta(productos: List<ProductoVendido>, total: Double) {
        ventas.add(Venta(productos, total))
    }


    override suspend fun resumen() = simular {
        ResumenMetricas(
            totalBusquedas = busquedas.size.toLong(),
            totalConsultasReceta = consultas.size.toLong(),
            totalPedidosPagados = ventas.size.toLong(),
            ventasTotales = ventas.sumOf { it.total }
        )
    }

    override suspend fun ingredientesMasBuscados(limite: Int) = simular {
        ranking(busquedas.flatMap { it.ingredientes }, limite)
    }

    override suspend fun recetasMasConsultadas(limite: Int) = simular {
        ranking(consultas, limite)
    }

    override suspend fun productosMasVendidos(limite: Int) = simular {
        ventas.flatMap { it.productos }
            .groupBy { it.id }
            .map { (id, lista) -> Conteo(id, lista.first().nombre, lista.sumOf { it.cantidad }.toLong()) }
            .sortedByDescending { it.cantidad }
            .take(limite)
    }

    override suspend fun combinacionesFrecuentes(limite: Int) = simular {
        val pares = mutableMapOf<Pair<Referencia, Referencia>, Long>()
        busquedas.forEach { busqueda ->
            val ordenados = busqueda.ingredientes.sortedBy { it.id }
            for (i in ordenados.indices) {
                for (j in i + 1 until ordenados.size) {
                    val par = ordenados[i] to ordenados[j]
                    pares[par] = (pares[par] ?: 0L) + 1
                }
            }
        }
        pares.map { (par, cantidad) ->
            Combinacion(par.first.id, par.first.nombre, par.second.id, par.second.nombre, cantidad)
        }
            .sortedByDescending { it.cantidad }
            .take(limite)
    }

    override suspend fun tendencias(dias: Int) = simular {
        val totalDias = dias.coerceIn(1, 365)
        val hasta = LocalDate.now()
        val desde = hasta.minusDays((totalDias - 1).toLong())
        val delPeriodo = busquedas.filter { !it.fecha.isBefore(desde) && !it.fecha.isAfter(hasta) }

        val porDia = (0 until totalDias).map { i ->
            val fecha = desde.plusDays(i.toLong())
            BusquedasDia(fecha.toString(), delPeriodo.count { it.fecha == fecha }.toLong())
        }

        Tendencia(
            desde = desde.toString(),
            hasta = hasta.toString(),
            totalBusquedas = delPeriodo.size.toLong(),
            busquedasPorDia = porDia,
            ingredientesDelPeriodo = ranking(delPeriodo.flatMap { it.ingredientes }, 5)
        )
    }
    private fun ranking(items: List<Referencia>, limite: Int): List<Conteo> =
        items.groupBy { it.id }
            .map { (id, lista) -> Conteo(id, lista.first().nombre, lista.size.toLong()) }
            .sortedByDescending { it.cantidad }
            .take(limite)

    private suspend fun <T> simular(bloque: () -> T): Result<T> {
        delay(300)
        return Result.success(bloque())
    }
}