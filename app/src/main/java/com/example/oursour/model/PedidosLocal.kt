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

data class PedidoLocal(
    val numero: Int,
    val idUsuario: Long,
    val estado: String,
    val items: List<ItemCarro>,
    val subtotal: Double,
    val costoDespacho: Double,
    val total: Double,
    val direccion: String
)

@Entity(tableName = "pedido")
data class PedidoEntity(
    @PrimaryKey val numero: Int,
    val idUsuario: Long,
    val estado: String,
    val subtotal: Double,
    val costoDespacho: Double,
    val total: Double,
    val direccion: String
)

@Entity(tableName = "pedido_item")
data class PedidoItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val numeroPedido: Int,
    val idProducto: Long,
    val nombre: String,
    val precioUnitario: Double,
    val cantidad: Int
)

object PedidosLocal {
    private var todos by mutableStateOf(listOf<PedidoLocal>())

    val pedidos: List<PedidoLocal>
        get() = todos.filter { it.idUsuario == idUsuarioActual() }

    private fun idUsuarioActual(): Long = AuthRepositoryRoom.usuarioActual()?.idUsuario ?: 0L

    fun siguienteNumero(): Int = (todos.maxOfOrNull { it.numero } ?: 0) + 1

    private val dao get() = BaseDatos.obtener().pedidoDao()
    private val alcance = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var direccionActual = ""

    var ultimoResultado = ""
    var ultimoNumero = 0

    var numeroElegido = 0

    suspend fun cargar() {
        todos = dao.listarPedidos().map { p ->
            PedidoLocal(
                numero = p.numero,
                idUsuario = p.idUsuario,
                estado = p.estado,
                items = dao.itemsDe(p.numero).map {
                    ItemCarro(Producto(it.idProducto, it.nombre, null, it.precioUnitario, it.cantidad, true), it.cantidad)
                },
                subtotal = p.subtotal,
                costoDespacho = p.costoDespacho,
                total = p.total,
                direccion = p.direccion
            )
        }
    }

    fun pagar(metodo: String): Boolean {
        if (metodo.contains("rechazada", ignoreCase = true)) {
            ultimoResultado = "RECHAZADO"
            return false
        }
        val numero = siguienteNumero()
        val nuevo = PedidoLocal(
            numero = numero,
            idUsuario = idUsuarioActual(),
            estado = "SOLICITADO",
            items = CarroLocal.items,
            subtotal = CarroLocal.subtotal,
            costoDespacho = CarroLocal.costoDespacho,
            total = CarroLocal.total,
            direccion = direccionActual
        )
        todos = todos + nuevo
        guardar(nuevo)
        ultimoResultado = "APROBADO"
        ultimoNumero = numero
        CarroLocal.vaciar()
        return true
    }

    private fun guardar(pedido: PedidoLocal) {
        val cabecera = PedidoEntity(
            numero = pedido.numero,
            idUsuario = pedido.idUsuario,
            estado = pedido.estado,
            subtotal = pedido.subtotal,
            costoDespacho = pedido.costoDespacho,
            total = pedido.total,
            direccion = pedido.direccion
        )
        val detalle = pedido.items.map {
            PedidoItemEntity(
                numeroPedido = pedido.numero,
                idProducto = it.producto.idProducto,
                nombre = it.producto.nombreComercial,
                precioUnitario = it.producto.precioUnitario,
                cantidad = it.cantidad
            )
        }
        alcance.launch { dao.guardarPedido(cabecera, detalle) }
    }
}