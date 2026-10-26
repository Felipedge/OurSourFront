package com.example.oursour.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oursour.model.Producto

@Composable
fun DetalleProductoScreen(
    cargando: Boolean,
    error: String?,
    producto: Producto?,
    cantidad: Int,
    agregando: Boolean,
    onCantidadMenos: () -> Unit,
    onCantidadMas: () -> Unit,
    onAgregarAlCarroClick: () -> Unit,
    onVolver: () -> Unit,
    onReintentar: () -> Unit
) {
    PantallaConBarra(
        titulo = producto?.nombreComercial ?: "Producto",
        onVolver = onVolver,
        barraInferior = {
            if (producto != null && !cargando) {
                val hayStock = producto.stockSimulado > 0
                Surface(tonalElevation = 3.dp) {
                    Button(
                        onClick = onAgregarAlCarroClick,
                        enabled = hayStock && !agregando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp)
                    ) {
                        when {
                            agregando -> CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            hayStock -> Text("Agregar al carro · ${formatearPesos(producto.precioUnitario * cantidad)}")
                            else -> Text("Sin stock")
                        }
                    }
                }
            }
        }
    ) { padding ->
        ContenidoConEstado(padding, cargando, error, onReintentar) {
            if (producto != null) {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(producto.nombreComercial, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Text(
                                formatearPesos(producto.precioUnitario),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                if (producto.stockSimulado > 0) "Stock disponible: ${producto.stockSimulado}" else "Sin stock",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (producto.stockSimulado > 0) MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.error
                            )

                            HorizontalDivider(Modifier.padding(vertical = 8.dp))

                            TituloSeccion("Descripción")
                            Text(
                                producto.descripcion ?: "Este producto no tiene descripción.",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            if (producto.stockSimulado > 0) {
                                TituloSeccion("Cantidad")
                                SelectorCantidad(
                                    cantidad = cantidad,
                                    onMenos = onCantidadMenos,
                                    onMas = onCantidadMas,
                                    maximo = producto.stockSimulado
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetalleProductoScreenPreview() {
    MaterialTheme {
        DetalleProductoScreen(
            cargando = false, error = null,
            producto = Producto(1, "Pisco 35°", "Pisco chileno, ideal para pisco sour. 750 ml.", 7990.0, 120, true),
            cantidad = 2, agregando = false,
            onCantidadMenos = {}, onCantidadMas = {}, onAgregarAlCarroClick = {},
            onVolver = {}, onReintentar = {}
        )
    }
}