package com.example.oursour.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oursour.model.Producto

@Composable
fun AdminProductosScreen(
    cargando: Boolean,
    error: String?,
    productos: List<Producto>,
    productoAEliminar: Producto?,
    onNuevoClick: () -> Unit,
    onEditarClick: (Long) -> Unit,
    onEliminarClick: (Long) -> Unit,
    onActivarClick: (Long) -> Unit,
    onConfirmarEliminarClick: () -> Unit,
    onCancelarEliminarClick: () -> Unit,
    onVolver: () -> Unit,
    onReintentar: () -> Unit
) {
    PantallaConBarra(
        titulo = "Catálogo (admin)",
        onVolver = onVolver,
        botonFlotante = {
            ExtendedFloatingActionButton(
                onClick = onNuevoClick,
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Nuevo") }
            )
        }
    ) { padding ->
        ContenidoConEstado(padding, cargando, error, onReintentar) {
            if (productos.isEmpty()) {
                EstadoVacio(
                    titulo = "No hay productos",
                    mensaje = "Crea el primer producto del catálogo.",
                    textoBoton = "Crear producto",
                    onBoton = onNuevoClick
                )
            } else {
                LazyColumn(contentPadding = PaddingValues(bottom = 88.dp)) {
                    items(productos, key = { it.idProducto }) { producto ->
                        ListItem(
                            headlineContent = { Text(producto.nombreComercial) },
                            supportingContent = {
                                Text("${formatearPesos(producto.precioUnitario)} · Stock: ${producto.stockSimulado}")
                            },
                            overlineContent = {
                                if (!producto.activo) {
                                    Text("DESACTIVADO", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            trailingContent = {
                                Row {
                                    IconButton(onClick = { onEditarClick(producto.idProducto) }) {
                                        Icon(Icons.Filled.Edit, contentDescription = "Editar ${producto.nombreComercial}")
                                    }
                                    if (producto.activo) {
                                        IconButton(onClick = { onEliminarClick(producto.idProducto) }) {
                                            Icon(
                                                Icons.Filled.Delete,
                                                contentDescription = "Desactivar ${producto.nombreComercial}",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    } else {
                                        TextButton(onClick = { onActivarClick(producto.idProducto) }) {
                                            Text("Activar")
                                        }
                                    }
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }

        if (productoAEliminar != null) {
            AlertDialog(
                onDismissRequest = onCancelarEliminarClick,
                title = { Text("Desactivar producto") },
                text = {
                    Text("\"${productoAEliminar.nombreComercial}\" dejará de mostrarse en el catálogo. Los pedidos anteriores no se ven afectados.")
                },
                confirmButton = {
                    TextButton(onClick = onConfirmarEliminarClick) {
                        Text("Desactivar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = onCancelarEliminarClick) { Text("Cancelar") }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminProductosScreenPreview() {
    MaterialTheme {
        AdminProductosScreen(
            cargando = false, error = null,
            productos = listOf(
                Producto(1, "Pisco 35°", "Pisco chileno, ideal para pisco sour. 750 ml.", 7990.0, 120, true),
                Producto(2, "Ron blanco", "Ron suave para mojitos y daiquiris. 750 ml.", 8990.0, 80, true),
                Producto(4, "Vodka", "Vodka neutro para cócteles y mezclas. 750 ml.", 9490.0, 0, true),
                Producto(10, "Licor de café", "Licor de café para espresso martini. 700 ml.", 9990.0, 0, false)
            ),
            productoAEliminar = null,
            onNuevoClick = {}, onEditarClick = {}, onEliminarClick = {}, onActivarClick = {},
            onConfirmarEliminarClick = {}, onCancelarEliminarClick = {}, onVolver = {}, onReintentar = {}
        )
    }
}