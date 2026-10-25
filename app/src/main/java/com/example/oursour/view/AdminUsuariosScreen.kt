package com.example.oursour.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oursour.model.DatosUsuario

@Composable
fun AdminUsuariosScreen(
    cargando: Boolean,
    error: String?,
    usuarios: List<DatosUsuario>,
    onVolver: () -> Unit,
    onReintentar: () -> Unit
) {
    PantallaConBarra(titulo = "Usuarios", onVolver = onVolver) { padding ->
        ContenidoConEstado(padding, cargando, error, onReintentar) {
            if (usuarios.isEmpty()) {
                EstadoVacio(titulo = "Sin usuarios", mensaje = "Todavía no hay cuentas registradas.")
            } else {
                LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
                    item {
                        Text(
                            "${usuarios.size} cuentas registradas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(usuarios, key = { it.idUsuario }) { usuario ->
                        ListItem(
                            headlineContent = { Text(usuario.nombre) },
                            supportingContent = {
                                Text("${usuario.email} · desde ${formatearFecha(usuario.fechaRegistro)}")
                            },
                            trailingContent = {
                                if (usuario.esAdmin) {
                                    Etiqueta(
                                        texto = "Admin",
                                        fondo = MaterialTheme.colorScheme.primaryContainer,
                                        colorTexto = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                } else {
                                    Etiqueta("Consumidor")
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminUsuariosScreenPreview() {
    MaterialTheme {
        AdminUsuariosScreen(
            cargando = false, error = null,
            usuarios = listOf(
                DatosUsuario(1, "Felipe", "fpalma@gmail.com", "CONSUMIDOR", "2026-09-20T10:15:00"),
                DatosUsuario(2, "Admin", "admin@oursour.cl", "ADMIN", "2026-09-01T09:00:00"),
                DatosUsuario(3, "Andres", "andres@gmail.com", "CONSUMIDOR", "2026-10-25T17:50:00")
            ),
            onVolver = {}, onReintentar = {}
        )
    }
}