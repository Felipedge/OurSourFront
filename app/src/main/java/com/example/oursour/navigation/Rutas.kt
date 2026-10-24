package com.example.oursour.navigation

object Rutas {
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val HOME = "home"
    const val PERFIL = "perfil"
    const val CATALOGO = "catalogo"
    const val DETALLE_PRODUCTO = "producto/{idProducto}"

    const val ADMIN_HOME = "admin"
    const val ADMIN_PRODUCTOS = "admin/productos"
    const val PRODUCTO_FORM = "admin/producto?idProducto={idProducto}"
    const val METRICAS = "admin/metricas"

    const val SELECCIONAR_INGREDIENTES = "ingredientes"
    const val INGREDIENTES_FRECUENTES = "ingredientes/frecuentes"
    const val RECETAS_RECOMENDADAS = "recetas"
    const val DETALLE_RECETA = "receta"
    const val CARRITO = "carrito"
    const val DESPACHO = "despacho"
    const val RESUMEN_COMPRA = "resumen"
    const val PAGO = "pago"
    const val RESULTADO_PAGO = "pago/resultado"
    const val MIS_PEDIDOS = "pedidos"
    const val DETALLE_PEDIDO = "pedido"

    fun detalleProducto(idProducto: Long) = "producto/$idProducto"

    fun productoForm(idProducto: Long? = null) =
        if (idProducto == null) "admin/producto" else "admin/producto?idProducto=$idProducto"
}