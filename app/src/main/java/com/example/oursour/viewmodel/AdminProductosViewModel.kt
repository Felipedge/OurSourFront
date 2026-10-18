package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.Producto
import com.example.oursour.model.ProductoRepository
import com.example.oursour.model.ProductoRepositoryLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminProductosUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val productos: List<Producto> = emptyList(),
    val productoAEliminar: Producto? = null
)

class AdminProductosViewModel(
    private val repository: ProductoRepository = ProductoRepositoryLocal
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminProductosUiState())
    val uiState: StateFlow<AdminProductosUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, error = null) }
            repository.listarTodos()
                .onSuccess { lista ->
                    _uiState.update { it.copy(cargando = false, productos = lista) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(cargando = false, error = e.message ?: "No se pudo cargar el catálogo") }
                }
        }
    }


    fun onEliminarClick(idProducto: Long) =
        _uiState.update { s -> s.copy(productoAEliminar = s.productos.firstOrNull { it.idProducto == idProducto }) }
    fun onCancelarEliminarClick() =
        _uiState.update { it.copy(productoAEliminar = null) }
    fun onConfirmarEliminarClick() {
        val producto = _uiState.value.productoAEliminar ?: return
        _uiState.update { it.copy(productoAEliminar = null) }
        viewModelScope.launch {
            repository.eliminar(producto.idProducto)
                .onSuccess { cargar() }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "No se pudo desactivar el producto") }
                }
        }
    }
}