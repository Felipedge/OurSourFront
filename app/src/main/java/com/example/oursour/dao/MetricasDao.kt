package com.example.oursour.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.oursour.model.BusquedaEntity
import com.example.oursour.model.BusquedaIngredienteEntity
import com.example.oursour.model.BusquedasDia
import com.example.oursour.model.Combinacion
import com.example.oursour.model.ConsultaRecetaEntity
import com.example.oursour.model.Conteo
import com.example.oursour.model.ProductoVendido
import com.example.oursour.model.Referencia
import com.example.oursour.model.VentaEntity
import com.example.oursour.model.VentaProductoEntity

@Dao
abstract class MetricasDao {

    @Insert
    abstract suspend fun insertarBusqueda(busqueda: BusquedaEntity): Long

    @Insert
    abstract suspend fun insertarIngredientesBusqueda(ingredientes: List<BusquedaIngredienteEntity>)

    @Insert
    abstract suspend fun insertarConsulta(consulta: ConsultaRecetaEntity)

    @Insert
    abstract suspend fun insertarVenta(venta: VentaEntity): Long

    @Insert
    abstract suspend fun insertarProductosVenta(productos: List<VentaProductoEntity>)

    @Transaction
    open suspend fun registrarBusqueda(fecha: String, ingredientes: List<Referencia>) {
        val idBusqueda = insertarBusqueda(BusquedaEntity(fecha = fecha))
        insertarIngredientesBusqueda(
            ingredientes.map {
                BusquedaIngredienteEntity(idBusqueda = idBusqueda, idIngrediente = it.id, nombre = it.nombre)
            }
        )
    }

    @Transaction
    open suspend fun registrarVenta(fecha: String, total: Double, productos: List<ProductoVendido>) {
        val idVenta = insertarVenta(VentaEntity(total = total, fecha = fecha))
        insertarProductosVenta(
            productos.map {
                VentaProductoEntity(idVenta = idVenta, idProducto = it.id, nombre = it.nombre, cantidad = it.cantidad)
            }
        )
    }

    @Query("SELECT COUNT(*) FROM busqueda")
    abstract suspend fun totalBusquedas(): Long

    @Query("SELECT COUNT(*) FROM consulta_receta")
    abstract suspend fun totalConsultas(): Long

    @Query("SELECT COUNT(*) FROM venta")
    abstract suspend fun totalVentas(): Long

    @Query("SELECT COALESCE(SUM(total), 0) FROM venta")
    abstract suspend fun montoVentas(): Double

    @Query(
        """
        SELECT idIngrediente AS id, nombre, COUNT(*) AS cantidad
        FROM busqueda_ingrediente
        GROUP BY idIngrediente
        ORDER BY cantidad DESC
        LIMIT :limite
        """
    )
    abstract suspend fun ingredientesMasBuscados(limite: Int): List<Conteo>

    @Query(
        """
        SELECT idReceta AS id, nombre, COUNT(*) AS cantidad
        FROM consulta_receta
        GROUP BY idReceta
        ORDER BY cantidad DESC
        LIMIT :limite
        """
    )
    abstract suspend fun recetasMasConsultadas(limite: Int): List<Conteo>

    @Query(
        """
        SELECT idProducto AS id, nombre, SUM(cantidad) AS cantidad
        FROM venta_producto
        GROUP BY idProducto
        ORDER BY cantidad DESC
        LIMIT :limite
        """
    )
    abstract suspend fun productosMasVendidos(limite: Int): List<Conteo>

    @Query(
        """
        SELECT a.idIngrediente AS idIngrediente1, a.nombre AS nombre1,
               b.idIngrediente AS idIngrediente2, b.nombre AS nombre2,
               COUNT(*) AS cantidad
        FROM busqueda_ingrediente a
        INNER JOIN busqueda_ingrediente b
            ON a.idBusqueda = b.idBusqueda AND a.idIngrediente < b.idIngrediente
        GROUP BY a.idIngrediente, b.idIngrediente
        ORDER BY cantidad DESC
        LIMIT :limite
        """
    )
    abstract suspend fun combinacionesFrecuentes(limite: Int): List<Combinacion>

    @Query("SELECT fecha, COUNT(*) AS busquedas FROM busqueda WHERE fecha >= :desde GROUP BY fecha")
    abstract suspend fun busquedasPorDiaDesde(desde: String): List<BusquedasDia>

    @Query(
        """
        SELECT bi.idIngrediente AS id, bi.nombre AS nombre, COUNT(*) AS cantidad
        FROM busqueda_ingrediente bi
        INNER JOIN busqueda b ON b.id = bi.idBusqueda
        WHERE b.fecha >= :desde
        GROUP BY bi.idIngrediente
        ORDER BY cantidad DESC
        LIMIT :limite
        """
    )
    abstract suspend fun ingredientesDesde(desde: String, limite: Int): List<Conteo>
}