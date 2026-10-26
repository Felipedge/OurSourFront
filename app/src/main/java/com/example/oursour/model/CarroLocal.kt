package com.example.oursour.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.oursour.dao.BaseDatos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

data class ItemCarro(
    val producto: Producto,
    val cantidad: Int
)

// ==================== Tabla de Room (@Entity) ====================
// Guarda una fila por producto del carro, para que no se pierda al cerrar la app

@Entity(tableName = "carro_item")
data class CarroItemEntity(
    @PrimaryKey val idProducto: Long,
    val nombre: String,
    val precioUnitario: Double,
    val stock: Int,
    val cantidad: Int
)

fun ItemCarro.aEntity() = CarroItemEntity(
    idProducto = producto.idProducto,
    nombre = producto.nombreComercial,
    precioUnitario = producto.precioUnitario,
    stock = producto.stockSimulado,
    cantidad = cantidad
)

fun CarroItemEntity.aItemCarro() = ItemCarro(
    producto = Producto(idProducto, nombre, null, precioUnitario, stock, true),
    cantidad = cantidad
)

// ==================== Carro compartido ====================
// Las pantallas leen "items" como antes; cada cambio ademas se guarda en Room

object CarroLocal {
    var items by mutableStateOf(listOf<ItemCarro>())

    private val dao get() = BaseDatos.obtener().carroDao()
    private val alcance = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // se llama una vez al abrir la app (MainActivity) para recuperar el carro guardado
    suspend fun cargar() {
        items = dao.listar().map { it.aItemCarro() }
    }

    fun agregar(producto: Producto) {
        val existente = items.firstOrNull { it.producto.idProducto == producto.idProducto }
        if (existente == null) {
            items = items + ItemCarro(producto, 1)
            guardar()
        } else {
            cambiarCantidad(producto.idProducto, existente.cantidad + 1)
        }
    }

    fun cambiarCantidad(idProducto: Long, nuevaCantidad: Int) {
        items = items.map {
            if (it.producto.idProducto == idProducto) it.copy(cantidad = nuevaCantidad) else it
        }
        guardar()
    }

    fun eliminar(idProducto: Long) {
        items = items.filter { it.producto.idProducto != idProducto }
        guardar()
    }

    fun vaciar() {
        items = listOf()
        guardar()
    }

    val cantidadTotal: Int
        get() = items.sumOf { it.cantidad }

    val subtotal: Double
        get() = items.sumOf { it.producto.precioUnitario * it.cantidad }

    //aqui tenemos el  Despacho: gratis desde $50.000, si no $3.990 (estrategia propia, el caso lo permite)
    val costoDespacho: Double
        get() = if (items.isEmpty() || subtotal >= 50000.0) 0.0 else 3990.0

    val total: Double
        get() = subtotal + costoDespacho

    // copia la lista actual a la tabla carro_item (en segundo plano)
    private fun guardar() {
        val copia = items.map { it.aEntity() }
        alcance.launch { dao.reemplazarTodo(copia) }
    }
}
