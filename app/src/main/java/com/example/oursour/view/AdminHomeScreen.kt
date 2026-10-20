package com.example.oursour.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oursour.model.ResumenMetricas

@Composable
fun AdminHomeScreen(
    nombreAdmin: String,
    cargando: Boolean,
    resumen: ResumenMetricas?,
    onGestionarCatalogoClick: () -> Unit,
    onVerMetricasClick: () -> Unit,
    onCerrarSesionClick: () -> Unit
) {
    PantallaConBarra(
        titulo = "Panel Our Sour",
        acciones = {
            TextButton(onClick = onCerrarSesionClick) {
                Text("Salir", color = MaterialTheme.colorScheme.onPrimary)
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
                    Text("Hola, $nombreAdmin", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Resumen general", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            item {
                if (cargando || resumen == null) {
                    Box(Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                        if (cargando) CircularProgressIndicator() else Text("Resumen no disponible")
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            TarjetaDato("Búsquedas", resumen.totalBusquedas.toString(), Modifier.weight(1f))
                            TarjetaDato("Recetas vistas", resumen.totalConsultasReceta.toString(), Modifier.weight(1f))
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            TarjetaDato("Pedidos pagados", resumen.totalPedidosPagados.toString(), Modifier.weight(1f))
                            TarjetaDato("Ventas", formatearPesos(resumen.ventasTotales), Modifier.weight(1f))
                        }
                    }
                }
            }

            item { OpcionAdmin("Gestionar catálogo", "Crear, editar y desactivar productos", onGestionarCatalogoClick) }
            item { OpcionAdmin("Ver métricas", "Ingredientes, recetas y tendencias de búsqueda", onVerMetricasClick) }
        }
    }
}

@Composable
private fun OpcionAdmin(titulo: String, descripcion: String, onClick: () -> Unit) {
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
fun AdminHomeScreenPreview() {
    MaterialTheme {
        AdminHomeScreen("Equipo Our Sour", false, ResumenMetricas(12, 30, 4, 45960.0), {}, {}, {})
    }
}