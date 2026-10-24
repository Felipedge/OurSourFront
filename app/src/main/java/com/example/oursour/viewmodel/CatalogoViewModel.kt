package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.Producto
import com.example.oursour.model.ProductoRepository
import com.example.oursour.model.ProductoRepositoryLocal
import com.example.oursour.model.leerCodigoProducto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CatalogoUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val busqueda: String = "",
    val todos: List<Producto> = emptyList(),
    val cantidadEnCarro: Int = 0,
    val mensajeQr: String? = null,
    val idProductoQr: Long? = null
) {
    val productos: List<Producto>
        get() = if (busqueda.isBlank()) todos
        else todos.filter { it.nombreComercial.contains(busqueda.trim(), ignoreCase = true) }
}

class CatalogoViewModel(
    private val repository: ProductoRepository = ProductoRepositoryLocal
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, error = null) }
            repository.listar()
                .onSuccess { lista ->
                    _uiState.update { it.copy(cargando = false, todos = lista) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(cargando = false, error = e.message ?: "No se pudo cargar el catálogo") }
                }
        }
    }

    fun onBusquedaChange(valor: String) =
        _uiState.update { it.copy(busqueda = valor) }

    fun onAgregarAlCarroClick(idProducto: Long) =
        _uiState.update { it.copy(cantidadEnCarro = it.cantidadEnCarro + 1) }

    fun onQrLeido(texto: String?) {
        if (texto == null) return
        val idProducto = leerCodigoProducto(texto)
        val producto = _uiState.value.todos.firstOrNull { it.idProducto == idProducto }
        when {
            idProducto == null ->
                _uiState.update { it.copy(mensajeQr = "Este código QR no es de Our Sour") }
            producto == null ->
                _uiState.update { it.copy(mensajeQr = "Ese producto no está disponible") }
            else ->
                _uiState.update { it.copy(mensajeQr = null, idProductoQr = producto.idProducto) }
        }
    }

    fun onQrNavegado() =
        _uiState.update { it.copy(idProductoQr = null) }
}