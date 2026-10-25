package com.example.oursour.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.oursour.model.BusquedaEntity
import com.example.oursour.model.BusquedaIngredienteEntity
import com.example.oursour.model.CarroItemEntity
import com.example.oursour.model.ConsultaRecetaEntity
import com.example.oursour.model.PedidoEntity
import com.example.oursour.model.PedidoItemEntity
import com.example.oursour.model.UsuarioEntity
import com.example.oursour.model.VentaEntity
import com.example.oursour.model.VentaProductoEntity

@Database(
    entities = [
        UsuarioEntity::class,
        BusquedaEntity::class,
        BusquedaIngredienteEntity::class,
        ConsultaRecetaEntity::class,
        VentaEntity::class,
        VentaProductoEntity::class,
        CarroItemEntity::class,
        PedidoEntity::class,
        PedidoItemEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class BaseDatos : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    abstract fun metricasDao(): MetricasDao

    abstract fun carroDao(): CarroDao

    abstract fun pedidoDao(): PedidoDao

    companion object {
        private var instancia: BaseDatos? = null

        fun iniciar(context: Context) {
            if (instancia == null) {
                instancia = Room.databaseBuilder(context.applicationContext, BaseDatos::class.java, "oursour.db")
                    .fallbackToDestructiveMigration(true)
                    .build()
            }
        }

        fun obtener(): BaseDatos =
            instancia ?: error("Falta llamar a BaseDatos.iniciar() en MainActivity")
    }
}