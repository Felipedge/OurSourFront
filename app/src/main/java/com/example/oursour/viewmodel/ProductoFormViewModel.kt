package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.ProductoRepository
import com.example.oursour.model.ProductoRepositoryLocal
import com.example.oursour.model.ProductoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProductoFormUiState(
    val idProducto: Long? = null,
    val cargando: Boolean = false,
    val nombre: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val stock: String = "",
    val errorNombre: String? = null,
    val errorDescripcion: String? = null,
    val errorPrecio: String? = null,
    val errorStock: String? = null,
    val errorGeneral: String? = null,
    val guardando: Boolean = false,
    val guardadoOk: Boolean = false
) {
    val esEdicion: Boolean get() = idProducto != null
}

class ProductoFormViewModel(
    private val repository: ProductoRepository = ProductoRepositoryLocal
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductoFormUiState())
    val uiState: StateFlow<ProductoFormUiState> = _uiState.asStateFlow()

    private var iniciado = false

    fun iniciar(idProducto: Long?) {
        if (iniciado) return
        iniciado = true
        if (idProducto == null) return   // crear: el formulario parte vacio

        // Editar: se cargan los datos actuales del producto en el formulario
        viewModelScope.launch {
            _uiState.update { it.copy(idProducto = idProducto, cargando = true) }
            repository.obtener(idProducto)
                .onSuccess { p ->
                    _uiState.update {
                        it.copy(
                            cargando = false,
                            nombre = p.nombreComercial,
                            descripcion = p.descripcion ?: "",
                            precio = p.precioUnitario.toLong().toString(),
                            stock = p.stockSimulado.toString()
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(cargando = false, errorGeneral = e.message) }
                }
        }
    }

    fun onNombreChange(v: String) = _uiState.update { it.copy(nombre = v, errorNombre = null) }

    fun onDescripcionChange(v: String) = _uiState.update { it.copy(descripcion = v, errorDescripcion = null) }


    fun onPrecioChange(v: String) {
        if (v.all { it.isDigit() }) _uiState.update { it.copy(precio = v, errorPrecio = null) }
    }

    fun onStockChange(v: String) {
        if (v.all { it.isDigit() }) _uiState.update { it.copy(stock = v, errorStock = null) }
    }

    fun onGuardarClick() {
        if (!validar()) return
        val s = _uiState.value
        val req = ProductoRequest(
            nombreComercial = s.nombre.trim(),
            descripcion = s.descripcion.trim().ifBlank { null },
            precioUnitario = s.precio.toDouble(),
            stockSimulado = s.stock.toInt()
        )

        viewModelScope.launch {
            _uiState.update { it.copy(guardando = true, errorGeneral = null) }
            val resultado = if (s.idProducto == null) repository.crear(req)
            else repository.actualizar(s.idProducto, req)
            resultado
                .onSuccess { _uiState.update { it.copy(guardando = false, guardadoOk = true) } }
                .onFailure { e ->
                    _uiState.update { it.copy(guardando = false, errorGeneral = e.message ?: "No se pudo guardar") }
                }
        }
    }
    private fun validar(): Boolean {
        val s = _uiState.value
        val errorNombre = when {
            s.nombre.isBlank() -> "El nombre comercial es obligatorio"
            s.nombre.length > 150 -> "El nombre comercial no puede tener más de 150 caracteres"
            else -> null
        }
        val errorDescripcion =
            if (s.descripcion.length > 500) "La descripción no puede tener más de 500 caracteres" else null
        val errorPrecio = when {
            s.precio.isBlank() -> "El precio unitario es obligatorio"
            (s.precio.toLongOrNull() ?: 0L) <= 0L -> "El precio unitario debe ser mayor a 0"
            s.precio.length > 8 -> "El precio admite hasta 8 dígitos"
            else -> null
        }
        val errorStock = when {
            s.stock.isBlank() -> "El stock es obligatorio"
            s.stock.toIntOrNull() == null -> "Stock inválido"
            else -> null
        }
        _uiState.update {
            it.copy(
                errorNombre = errorNombre,
                errorDescripcion = errorDescripcion,
                errorPrecio = errorPrecio,
                errorStock = errorStock
            )
        }
        return listOf(errorNombre, errorDescripcion, errorPrecio, errorStock).all { it == null }
    }
}