package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.CarroLocal
import com.example.oursour.model.Producto
import com.example.oursour.model.ProductoRepository
import com.example.oursour.model.ProductoRepositoryLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalleProductoUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val producto: Producto? = null,
    val cantidad: Int = 1,
    val agregando: Boolean = false,
    val agregadoAlCarro: Boolean = false
)

class DetalleProductoViewModel(
    private val repository: ProductoRepository = ProductoRepositoryLocal
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleProductoUiState())
    val uiState: StateFlow<DetalleProductoUiState> = _uiState.asStateFlow()

    private var idActual: Long? = null

    fun cargar(idProducto: Long) {
        if (idActual == idProducto && _uiState.value.producto != null) return
        idActual = idProducto

        viewModelScope.launch {
            _uiState.update { DetalleProductoUiState(cargando = true) }
            repository.obtener(idProducto)
                .onSuccess { p ->
                    _uiState.update { it.copy(cargando = false, producto = p) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(cargando = false, error = e.message ?: "No se pudo cargar el producto") }
                }
        }
    }

    fun onReintentar() {
        idActual?.let { id ->
            idActual = null
            cargar(id)
        }
    }

    fun onCantidadMenos() =
        _uiState.update { it.copy(cantidad = (it.cantidad - 1).coerceAtLeast(1)) }


    fun onCantidadMas() =
        _uiState.update {
            val maximo = it.producto?.stockSimulado ?: 1
            it.copy(cantidad = (it.cantidad + 1).coerceAtMost(maximo))
        }

    fun onAgregarAlCarroClick() {
        val estado = _uiState.value
        val producto = estado.producto ?: return
        val yaEnCarro = CarroLocal.items.firstOrNull { it.producto.idProducto == producto.idProducto }?.cantidad ?: 0
        CarroLocal.agregar(producto)
        CarroLocal.cambiarCantidad(
            producto.idProducto,
            (yaEnCarro + estado.cantidad).coerceAtMost(producto.stockSimulado)
        )
        _uiState.update { it.copy(agregadoAlCarro = true) }
    }

    fun onAvisoMostrado() =
        _uiState.update { it.copy(agregadoAlCarro = false) }
}