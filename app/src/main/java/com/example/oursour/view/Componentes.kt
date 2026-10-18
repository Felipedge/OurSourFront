

package com.example.oursour.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.oursour.model.Producto
import java.text.NumberFormat
import java.util.Locale


fun formatearPesos(valor: Number): String {
    val formato = NumberFormat.getIntegerInstance(Locale.forLanguageTag("es-CL"))
    return "\$" + formato.format(Math.round(valor.toDouble()))
}


fun formatearFecha(fechaIso: String): String {
    val partes = fechaIso.take(10).split("-")
    return if (partes.size == 3) "${partes[2]}-${partes[1]}-${partes[0]}" else fechaIso
}

fun textoCategoria(categoria: String): String = when (categoria) {
    "DESTILADO" -> "Destilados"
    "LICOR" -> "Licores"
    "FRUTA" -> "Frutas"
    "JUGO" -> "Jugos"
    "MIXER" -> "Mixers"
    "OUR_SOUR" -> "Productos Our Sour"
    else -> "Otros"
}

fun textoTipoReceta(tipo: String): String = if (tipo == "MOCKTAIL") "Mocktail" else "Cóctel"

fun textoEstadoPedido(estado: String): String = when (estado) {
    "PENDIENTE_PAGO" -> "Pendiente de pago"
    "SOLICITADO" -> "Solicitado"
    "EN_PREPARACION" -> "En preparación"
    "PAGO_RECHAZADO" -> "Pago rechazado"
    else -> estado
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaConBarra(
    titulo: String,
    onVolver: (() -> Unit)? = null,
    acciones: @Composable RowScope.() -> Unit = {},
    barraInferior: @Composable () -> Unit = {},
    botonFlotante: @Composable () -> Unit = {},
    contenido: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    if (onVolver != null) {
                        IconButton(onClick = onVolver) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                },
                actions = acciones,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = barraInferior,
        floatingActionButton = botonFlotante,
        containerColor = MaterialTheme.colorScheme.background,
        content = contenido
    )
}


@Composable
fun ContenidoConEstado(
    padding: PaddingValues,
    cargando: Boolean,
    error: String?,
    onReintentar: () -> Unit,
    contenido: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        when {
            cargando -> EstadoCargando()
            error != null -> EstadoError(error, onReintentar)
            else -> contenido()
        }
    }
}

@Composable
fun EstadoCargando() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun EstadoError(mensaje: String, onReintentar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Algo salió mal", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(
            mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onReintentar) { Text("Reintentar") }
    }
}

@Composable
fun EstadoVacio(
    titulo: String,
    mensaje: String,
    textoBoton: String? = null,
    onBoton: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(titulo, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text(
            mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (textoBoton != null) {
            Spacer(Modifier.height(16.dp))
            Button(onClick = onBoton) { Text(textoBoton) }
        }
    }
}


@Composable
fun TituloSeccion(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}


@Composable
fun Etiqueta(
    texto: String,
    fondo: Color = MaterialTheme.colorScheme.secondaryContainer,
    colorTexto: Color = MaterialTheme.colorScheme.onSecondaryContainer
) {
    Surface(color = fondo, shape = RoundedCornerShape(50)) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelMedium,
            color = colorTexto,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun EtiquetaEstadoPedido(estado: String) {
    val esError = estado == "PAGO_RECHAZADO"
    Etiqueta(
        texto = textoEstadoPedido(estado),
        fondo = if (esError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
        colorTexto = if (esError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSecondaryContainer
    )
}


@Composable
fun FilaMonto(etiqueta: String, valor: String, destacado: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            etiqueta,
            style = if (destacado) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            valor,
            style = if (destacado) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun SelectorCantidad(
    cantidad: Int,
    onMenos: () -> Unit,
    onMas: () -> Unit,
    minimo: Int = 1,
    maximo: Int = 99
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedButton(
            onClick = onMenos,
            enabled = cantidad > minimo,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(36.dp)
        ) { Text("−") }
        Text(
            text = cantidad.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.widthIn(min = 36.dp),
            textAlign = TextAlign.Center
        )
        OutlinedButton(
            onClick = onMas,
            enabled = cantidad < maximo,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(36.dp)
        ) { Text("+") }
    }
}


@Composable
fun TarjetaProducto(
    producto: Producto,
    onClick: () -> Unit,
    onAgregar: () -> Unit
) {
    val hayStock = producto.stockSimulado > 0
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(producto.nombreComercial, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            producto.descripcion?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(formatearPesos(producto.precioUnitario), style = MaterialTheme.typography.titleLarge)
                    if (!hayStock) {
                        Text("Sin stock", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.error)
                    }
                }
                Button(onClick = onAgregar, enabled = hayStock) { Text("Agregar") }
            }
        }
    }
}


@Composable
fun TarjetaDato(etiqueta: String, valor: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(etiqueta, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(valor, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}