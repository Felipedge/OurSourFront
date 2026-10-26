package com.example.oursour.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oursour.model.BusquedasDia
import com.example.oursour.model.Combinacion
import com.example.oursour.model.Conteo
import com.example.oursour.model.ResumenMetricas

@Composable
fun MetricasScreen(
    cargando: Boolean,
    error: String?,
    dias: Int,
    resumen: ResumenMetricas?,
    busquedasPorDia: List<BusquedasDia>,
    ingredientesTop: List<Conteo>,
    recetasTop: List<Conteo>,
    productosTop: List<Conteo>,
    combinaciones: List<Combinacion>,
    onDiasChange: (Int) -> Unit,
    onVolver: () -> Unit,
    onReintentar: () -> Unit
) {
    PantallaConBarra(titulo = "Métricas", onVolver = onVolver) { padding ->
        ContenidoConEstado(padding, cargando, error, onReintentar) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                resumen?.let { r ->
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                TarjetaDato("Búsquedas", r.totalBusquedas.toString(), Modifier.weight(1f))
                                TarjetaDato("Recetas vistas", r.totalConsultasReceta.toString(), Modifier.weight(1f))
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                TarjetaDato("Pedidos pagados", r.totalPedidosPagados.toString(), Modifier.weight(1f))
                                TarjetaDato("Ventas", formatearPesos(r.ventasTotales), Modifier.weight(1f))
                            }
                        }
                    }
                }

                item {
                    val diasConBusquedas = busquedasPorDia.filter { it.busquedas > 0 }.reversed()
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Búsquedas por día", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf(7, 30, 90).forEach { opcion ->
                                    FilterChip(
                                        selected = dias == opcion,
                                        onClick = { onDiasChange(opcion) },
                                        label = { Text("$opcion días") }
                                    )
                                }
                            }
                            Text(
                                "Total del periodo: ${busquedasPorDia.sumOf { it.busquedas }}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (diasConBusquedas.isEmpty()) {
                                Text("Sin búsquedas en este periodo", style = MaterialTheme.typography.bodyMedium)
                            }
                            diasConBusquedas.forEach { dia ->
                                FilaValor(formatearFecha(dia.fecha), dia.busquedas.toString())
                            }
                        }
                    }
                }

                item { TarjetaRanking("Ingredientes más buscados", ingredientesTop) }
                item { TarjetaRanking("Recetas más consultadas", recetasTop) }
                item { TarjetaRanking("Productos más vendidos", productosTop) }
                item {
                    TarjetaRanking(
                        "Combinaciones frecuentes",
                        combinaciones.map { Conteo(it.idIngrediente1, "${it.nombre1} + ${it.nombre2}", it.cantidad) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaRanking(titulo: String, datos: List<Conteo>) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            if (datos.isEmpty()) {
                Text("Sin datos todavía", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            datos.forEachIndexed { indice, dato ->
                FilaValor("${indice + 1}. ${dato.nombre}", dato.cantidad.toString())
            }
        }
    }
}

@Composable
private fun FilaValor(texto: String, valor: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(texto, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Text(valor, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MetricasScreenPreview() {
    MaterialTheme {
        MetricasScreen(
            cargando = false, error = null, dias = 7,
            resumen = ResumenMetricas(12, 30, 4, 45960.0),
            busquedasPorDia = listOf(
                BusquedasDia("2026-10-13", 0), BusquedasDia("2026-10-14", 3), BusquedasDia("2026-10-15", 0),
                BusquedasDia("2026-10-16", 5), BusquedasDia("2026-10-17", 2), BusquedasDia("2026-10-18", 0),
                BusquedasDia("2026-10-19", 2)
            ),
            ingredientesTop = listOf(Conteo(1, "Gin", 9), Conteo(5, "Limón", 7), Conteo(2, "Pisco", 4)),
            recetasTop = listOf(Conteo(2, "Gin Sour", 12), Conteo(1, "Pisco Sour", 8)),
            productosTop = listOf(Conteo(1, "Pisco 35°", 5), Conteo(5, "Gin", 3)),
            combinaciones = listOf(Combinacion(1, "Gin", 12, "Tónica", 6), Combinacion(2, "Pisco", 5, "Limón", 4)),
            onDiasChange = {}, onVolver = {}, onReintentar = {}
        )
    }
}