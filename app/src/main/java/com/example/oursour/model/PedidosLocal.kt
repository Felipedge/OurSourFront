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
    val estado: String,
    val items: List<ItemCarro>,
    val subtotal: Double,
    val costoDespacho: Double,
    val total: Double,
    val direccion: String
)

// ==================== Tablas de Room (@Entity) ====================

@Entity(tableName = "pedido")
data class PedidoEntity(
    @PrimaryKey val numero: Int,
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

// ==================== Pedidos ====================

object PedidosLocal {
    var pedidos by mutableStateOf(listOf<PedidoLocal>())

    private val dao get() = BaseDatos.obtener().pedidoDao()
    private val alcance = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    //aqui tenemos  Las dirección la guarda la pantalla de Despacho
    var direccionActual = ""

    //aqui tenemos los  Resultado del último intento de pago: "APROBADO" o "RECHAZADO"
    var ultimoResultado = ""
    var ultimoNumero = 0

    var numeroElegido = 0

    // se llama una vez al abrir la app (MainActivity) para recuperar los pedidos guardados
    suspend fun cargar() {
        pedidos = dao.listarPedidos().map { p ->
            PedidoLocal(
                numero = p.numero,
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

    // aqui tenemos los  Pago sandbox: se aprueba salvo que el método escrito sea "rechazada"
    fun pagar(metodo: String): Boolean {
        if (metodo.contains("rechazada", ignoreCase = true)) {
            ultimoResultado = "RECHAZADO"
            return false
        }
        val numero = (pedidos.maxOfOrNull { it.numero } ?: 0) + 1
        val nuevo = PedidoLocal(
            numero = numero,
            estado = "SOLICITADO",
            items = CarroLocal.items,
            subtotal = CarroLocal.subtotal,
            costoDespacho = CarroLocal.costoDespacho,
            total = CarroLocal.total,
            direccion = direccionActual
        )
        pedidos = pedidos + nuevo
        guardar(nuevo)
        ultimoResultado = "APROBADO"
        ultimoNumero = numero
        CarroLocal.vaciar()
        return true
    }

    // guarda el pedido y sus productos en Room (en segundo plano)
    private fun guardar(pedido: PedidoLocal) {
        val cabecera = PedidoEntity(
            numero = pedido.numero,
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

