package com.example.oursour.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductoFormScreen(
    esEdicion: Boolean,
    cargando: Boolean,
    nombre: String,
    descripcion: String,
    precio: String,
    stock: String,
    errorNombre: String?,
    errorDescripcion: String?,
    errorPrecio: String?,
    errorStock: String?,
    errorGeneral: String?,
    guardando: Boolean,
    onNombreChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    onStockChange: (String) -> Unit,
    onGuardarClick: () -> Unit,
    onVolver: () -> Unit
) {
    PantallaConBarra(
        titulo = if (esEdicion) "Editar producto" else "Nuevo producto",
        onVolver = onVolver,
        barraInferior = {
            if (!cargando) {
                Surface(tonalElevation = 3.dp) {
                    Button(
                        onClick = onGuardarClick,
                        enabled = !guardando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp)
                    ) {
                        if (guardando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(if (esEdicion) "Guardar cambios" else "Crear producto")
                        }
                    }
                }
            }
        }
    ) { padding ->
        ContenidoConEstado(padding, cargando, error = null, onReintentar = {}) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                item {
                    Column {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = onNombreChange,
                            label = { Text("Nombre comercial") },
                            isError = errorNombre != null,
                            supportingText = { Text(errorNombre ?: "${nombre.length}/150") },
                            singleLine = true,
                            enabled = !guardando,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = onDescripcionChange,
                            label = { Text("Descripción (opcional)") },
                            isError = errorDescripcion != null,
                            supportingText = { Text(errorDescripcion ?: "${descripcion.length}/500") },
                            minLines = 3,
                            maxLines = 5,
                            enabled = !guardando,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = precio,
                                onValueChange = onPrecioChange,
                                label = { Text("Precio") },
                                prefix = { Text("\$") },
                                isError = errorPrecio != null,
                                supportingText = { errorPrecio?.let { Text(it) } },
                                singleLine = true,
                                enabled = !guardando,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = stock,
                                onValueChange = onStockChange,
                                label = { Text("Stock") },
                                isError = errorStock != null,
                                supportingText = { errorStock?.let { Text(it) } },
                                singleLine = true,
                                enabled = !guardando,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        errorGeneral?.let {
                            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductoFormScreenPreview() {
    MaterialTheme {
        ProductoFormScreen(
            esEdicion = false, cargando = false,
            nombre = "Ron añejo", descripcion = "", precio = "0", stock = "50",
            errorNombre = null, errorDescripcion = null, errorPrecio = "El precio unitario debe ser mayor a 0",
            errorStock = null, errorGeneral = null, guardando = false,
            onNombreChange = {}, onDescripcionChange = {}, onPrecioChange = {}, onStockChange = {},
            onGuardarClick = {}, onVolver = {}
        )
    }
}