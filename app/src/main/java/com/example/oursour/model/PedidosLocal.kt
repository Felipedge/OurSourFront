package com.example.oursour.model



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class PedidoLocal(
    val numero: Int,
    val estado: String,
    val items: List<ItemCarro>,
    val subtotal: Double,
    val costoDespacho: Double,
    val total: Double,
    val direccion: String
)

object PedidosLocal {
    var pedidos by mutableStateOf(listOf<PedidoLocal>())

    //aqui tenemos  Las dirección la guarda la pantalla de Despacho
    var direccionActual = ""

    //aqui tenemos los  Resultado del último intento de pago: "APROBADO" o "RECHAZADO"
    var ultimoResultado = ""
    var ultimoNumero = 0

    var numeroElegido = 0

    // aqui tenemos los  Pago sandbox: se aprueba salvo que el método escrito sea "rechazada"
    fun pagar(metodo: String): Boolean {
        if (metodo.contains("rechazada", ignoreCase = true)) {
            ultimoResultado = "RECHAZADO"
            return false
        }
        val numero = pedidos.size + 1
        pedidos = pedidos + PedidoLocal(
            numero = numero,
            estado = "SOLICITADO",
            items = CarroLocal.items,
            subtotal = CarroLocal.subtotal,
            costoDespacho = CarroLocal.costoDespacho,
            total = CarroLocal.total,
            direccion = direccionActual
        )
        ultimoResultado = "APROBADO"
        ultimoNumero = numero
        CarroLocal.vaciar()
        return true
    }
}