package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.AuthRepository
import com.example.oursour.model.AuthRepositoryRoom
import com.example.oursour.model.DatosUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminUsuariosUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val usuarios: List<DatosUsuario> = emptyList()
)

class AdminUsuariosViewModel(
    private val repository: AuthRepository = AuthRepositoryRoom
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUsuariosUiState())
    val uiState: StateFlow<AdminUsuariosUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, error = null) }
            repository.listarUsuarios()
                .onSuccess { lista ->
                    _uiState.update { it.copy(cargando = false, usuarios = lista) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(cargando = false, error = e.message ?: "No se pudieron cargar los usuarios") }
                }
        }
    }
}