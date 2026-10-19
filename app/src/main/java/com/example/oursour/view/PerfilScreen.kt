package com.example.oursour.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oursour.model.DatosUsuario

@Composable
fun PerfilScreen(
    usuario: DatosUsuario?,
    onMisPedidosClick: () -> Unit,
    onIngredientesFrecuentesClick: () -> Unit,
    onCerrarSesionClick: () -> Unit,
    onVolver: () -> Unit
) {
    PantallaConBarra(titulo = "Mi perfil", onVolver = onVolver) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TarjetaPerfil(usuario) }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TituloSeccion("Tu barra")
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AccesoBarra(
                            icono = Icons.AutoMirrored.Filled.List,
                            titulo = "Mis pedidos",
                            descripcion = "Compras y su estado",
                            onClick = onMisPedidosClick,
                            modifier = Modifier.weight(1f)
                        )
                        AccesoBarra(
                            icono = Icons.Filled.Favorite,
                            titulo = "Mis ingredientes",
                            descripcion = "Lo que más usas",
                            onClick = onIngredientesFrecuentesClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            usuario?.let { u ->
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        TituloSeccion("Datos de la cuenta")
                        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                            Column {
                                FilaDato(Icons.Filled.Email, "Correo", u.email)
                                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                                FilaDato(Icons.Filled.DateRange, "Fecha de registro", formatearFecha(u.fechaRegistro))
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    "Disfruta con moderación. Venta solo a mayores de 18 años.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedButton(
                    onClick = onCerrarSesionClick,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión")
                }
            }
        }
    }
}

@Composable
private fun TarjetaPerfil(usuario: DatosUsuario?) {
    val colorTexto = MaterialTheme.colorScheme.onPrimary
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary)
                ),
                RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .border(2.dp, colorTexto, CircleShape)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    iniciales(usuario?.nombre),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    usuario?.nombre ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                )
                Text(
                    usuario?.email ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorTexto.copy(alpha = 0.85f)
                )
                Surface(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(50)) {
                    Text(
                        if (usuario?.esAdmin == true) "Administrador" else "Consumidor",
                        style = MaterialTheme.typography.labelMedium,
                        color = colorTexto,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AccesoBarra(
    icono: ImageVector,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(Modifier.padding(16.dp)) {
            Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
            Spacer(Modifier.height(12.dp))
            Text(
                titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun FilaDato(icono: ImageVector, etiqueta: String, valor: String) {
    ListItem(
        leadingContent = { Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
        overlineContent = { Text(etiqueta) },
        headlineContent = { Text(valor) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

private fun iniciales(nombre: String?): String =
    nombre?.trim()
        ?.split(" ")
        ?.filter { it.isNotBlank() }
        ?.take(2)
        ?.joinToString("") { it.first().uppercase() }
        ?.ifBlank { "?" }
        ?: "?"

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    MaterialTheme {
        PerfilScreen(
            DatosUsuario(1, "Camila Rojas", "camila@oursour.cl", "CONSUMIDOR", "2026-09-20T10:15:00"),
            {}, {}, {}, {}
        )
    }
}