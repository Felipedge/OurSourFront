package com.example.oursour.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    nombreUsuario: String,
    cantidadEnCarro: Int,
    onBuscarRecetasClick: () -> Unit,
    onIngredientesFrecuentesClick: () -> Unit,
    onCatalogoClick: () -> Unit,
    onCarroClick: () -> Unit,
    onMisPedidosClick: () -> Unit,
    onPerfilClick: () -> Unit
) {
    PantallaConBarra(
        titulo = "Our Sour",
        acciones = {
            IconButton(onClick = onCarroClick) {
                BadgedBox(badge = {
                    if (cantidadEnCarro > 0) Badge { Text(cantidadEnCarro.toString()) }
                }) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Mi carro")
                }
            }
            IconButton(onClick = onPerfilClick) {
                Icon(Icons.Filled.Person, contentDescription = "Mi perfil")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column {
                    Text("Hola, $nombreUsuario", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        "¿Qué preparamos hoy?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onBuscarRecetasClick),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("Buscar recetas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("Elige lo que tienes en casa y te decimos qué preparar", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            item { OpcionHome("Mis ingredientes frecuentes", "Los que más usas, listos para buscar", onIngredientesFrecuentesClick) }
            item { OpcionHome("Productos", "Licores y destilados", onCatalogoClick) }
            item { OpcionHome("Mis pedidos", "Revisa el estado de tus compras", onMisPedidosClick) }
        }
    }
}

@Composable
private fun OpcionHome(titulo: String, descripcion: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(descripcion, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen("Camila", 3, {}, {}, {}, {}, {}, {})
    }
}