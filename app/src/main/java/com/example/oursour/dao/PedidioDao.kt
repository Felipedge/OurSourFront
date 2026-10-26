
package com.example.oursour.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.oursour.model.PedidoEntity
import com.example.oursour.model.PedidoItemEntity

@Dao
abstract class PedidoDao {

    @Insert
    abstract suspend fun insertarPedido(pedido: PedidoEntity)

    @Insert
    abstract suspend fun insertarItems(items: List<PedidoItemEntity>)

    @Query("SELECT * FROM pedido ORDER BY numero")
    abstract suspend fun listarPedidos(): List<PedidoEntity>

    @Query("SELECT * FROM pedido_item WHERE numeroPedido = :numero")
    abstract suspend fun itemsDe(numero: Int): List<PedidoItemEntity>

    // guarda la cabecera del pedido y sus productos juntos
    @Transaction
    open suspend fun guardarPedido(pedido: PedidoEntity, items: List<PedidoItemEntity>) {
        insertarPedido(pedido)
        insertarItems(items)
    }
}

