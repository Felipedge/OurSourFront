package com.example.oursour.dao




import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.oursour.model.CarroItemEntity

@Dao
abstract class CarroDao {

    @Query("SELECT * FROM carro_item")
    abstract suspend fun listar(): List<CarroItemEntity>

    @Insert
    abstract suspend fun insertarTodos(items: List<CarroItemEntity>)

    @Query("DELETE FROM carro_item")
    abstract suspend fun borrarTodo()

    // borra el carro guardado y escribe el nuevo, como una sola operacion
    @Transaction
    open suspend fun reemplazarTodo(items: List<CarroItemEntity>) {
        borrarTodo()
        insertarTodos(items)
    }
}