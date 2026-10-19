package com.example.oursour.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oursour.model.Producto

@Composable
fun CatalogoScreen(
    cargando: Boolean,
    error: String?,
    busqueda: String,
    productos: List<Producto>,
    cantidadEnCarro: Int,
    onBusquedaChange: (String) -> Unit,
    onProductoClick: (Long) -> Unit,
    onAgregarAlCarroClick: (Long) -> Unit,
    onCarroClick: () -> Unit,
    onVolver: () -> Unit,
    onReintentar: () -> Unit
) {
    PantallaConBarra(
        titulo = "Productos",
        onVolver = onVolver,
        acciones = {
            IconButton(onClick = onCarroClick) {
                BadgedBox(badge = {
                    if (cantidadEnCarro > 0) Badge { Text(cantidadEnCarro.toString()) }
                }) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Mi carro")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = onBusquedaChange,
                placeholder = { Text("Buscar producto") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            ContenidoConEstado(PaddingValues(0.dp), cargando, error, onReintentar) {
                if (productos.isEmpty()) {
                    EstadoVacio(
                        titulo = "Sin resultados",
                        mensaje = if (busqueda.isBlank()) "Aún no hay productos disponibles."
                        else "No encontramos productos para \"$busqueda\"."
                    )
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(productos, key = { it.idProducto }) { producto ->
                            TarjetaProducto(
                                producto = producto,
                                onClick = { onProductoClick(producto.idProducto) },
                                onAgregar = { onAgregarAlCarroClick(producto.idProducto) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CatalogoScreenPreview() {
    MaterialTheme {
        CatalogoScreen(
            cargando = false, error = null, busqueda = "",
            productos = listOf(
                Producto(1, "Pisco 35° Capel", "Pisco chileno, ideal para pisco sour. 750 ml.", 7990.0, 20, true),
                Producto(2, "Ron blanco Mitjans", "Ron suave para mojitos y daiquiris. 750 ml.", 8990.0, 0, true),
                Producto(3, "Vodka Absolut ", "Vodka neutro para cócteles y mezclas. 750 ml.", 9490.0, 10, true),
                Producto(4, "Pulpa de Naranja", "Pulpa de naranja 100% natural. 750 ml.", 5690.0, 5, true),

            ),
            cantidadEnCarro = 2,
            onBusquedaChange = {}, onProductoClick = {}, onAgregarAlCarroClick = {},
            onCarroClick = {}, onVolver = {}, onReintentar = {}
        )
    }
}