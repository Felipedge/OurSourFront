package com.example.oursour.model

import kotlinx.coroutines.delay


data class Producto(
    val idProducto: Long,
    val nombreComercial: String,
    val descripcion: String?,
    val precioUnitario: Double,
    val stockSimulado: Int,
    val activo: Boolean
) {
    val hayStock: Boolean get() = stockSimulado > 0
}
data class ProductoRequest(
    val nombreComercial: String,   // obligatorio, maximo 150
    val descripcion: String?,      // maximo 500
    val precioUnitario: Double,    // mayor a 0
    val stockSimulado: Int         // 0 o mas
)


interface ProductoRepository {

    suspend fun listar(): Result<List<Producto>>
    suspend fun listarTodos(): Result<List<Producto>>
    suspend fun obtener(idProducto: Long): Result<Producto>
    suspend fun crear(req: ProductoRequest): Result<Producto>
    suspend fun actualizar(idProducto: Long, req: ProductoRequest): Result<Producto>
    suspend fun eliminar(idProducto: Long): Result<Unit>
}

object ProductoRepositoryLocal : ProductoRepository {

    private val productos = mutableListOf(
        Producto(1, "Pisco 35°", "Pisco chileno, ideal para pisco sour. 750 ml.", 7990.0, 120, true),
        Producto(2, "Ron blanco", "Ron suave para mojitos y daiquiris. 750 ml.", 8990.0, 80, true),
        Producto(3, "Ron dorado", "Ron añejado, para cuba libre y cócteles tropicales. 750 ml.", 10990.0, 60, true),
        Producto(4, "Vodka", "Vodka neutro para cócteles y mezclas. 750 ml.", 9490.0, 0, true),
        Producto(5, "Gin", "Gin seco, ideal para gin tonic y gin sour. 750 ml.", 14990.0, 70, true),
        Producto(6, "Tequila blanco", "Tequila para margaritas y palomas. 750 ml.", 15990.0, 40, true),
        Producto(7, "Whisky", "Whisky para whisky sour y old fashioned. 750 ml.", 18990.0, 35, true),
        Producto(8, "Licor de naranja", "Licor triple sec para margaritas y cosmopolitan. 700 ml.", 8490.0, 55, true),
        Producto(9, "Vermut rojo", "Vermut dulce para negroni y manhattan. 1 L.", 6990.0, 45, true),
        Producto(10, "Licor de café", "Licor de café para espresso martini. 700 ml.", 9990.0, 0, false)
    )

    override suspend fun listar(): Result<List<Producto>> {
        delay(400)
        return Result.success(productos.filter { it.activo }.sortedBy { it.nombreComercial })
    }

    override suspend fun listarTodos(): Result<List<Producto>> {
        delay(400)
        return Result.success(productos.sortedBy { it.nombreComercial })
    }

    override suspend fun obtener(idProducto: Long): Result<Producto> {
        delay(300)
        return productos.firstOrNull { it.idProducto == idProducto }
            ?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException("Producto $idProducto no encontrado"))
    }

    override suspend fun crear(req: ProductoRequest): Result<Producto> {
        delay(400)
        val nuevo = Producto(
            idProducto = (productos.maxOfOrNull { it.idProducto } ?: 0) + 1,
            nombreComercial = req.nombreComercial.trim(),
            descripcion = req.descripcion?.trim()?.ifBlank { null },
            precioUnitario = req.precioUnitario,
            stockSimulado = req.stockSimulado,
            activo = true
        )
        productos.add(nuevo)
        return Result.success(nuevo)
    }

    override suspend fun actualizar(idProducto: Long, req: ProductoRequest): Result<Producto> {
        delay(400)
        val indice = productos.indexOfFirst { it.idProducto == idProducto }
        if (indice == -1) return Result.failure(NoSuchElementException("Producto $idProducto no encontrado"))
        val actualizado = productos[indice].copy(
            nombreComercial = req.nombreComercial.trim(),
            descripcion = req.descripcion?.trim()?.ifBlank { null },
            precioUnitario = req.precioUnitario,
            stockSimulado = req.stockSimulado
        )
        productos[indice] = actualizado
        return Result.success(actualizado)
    }
        override suspend fun eliminar(idProducto: Long): Result<Unit> {
        delay(300)
        val indice = productos.indexOfFirst { it.idProducto == idProducto }
        if (indice == -1) return Result.failure(NoSuchElementException("Producto $idProducto no encontrado"))
        productos[indice] = productos[indice].copy(activo = false)
        return Result.success(Unit)
    }
}