package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.AuthRepository
import com.example.oursour.model.AuthRepositoryLocal
import com.example.oursour.model.DatosUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class PerfilUiState(
    val usuario: DatosUsuario? = null,
    val sesionCerrada: Boolean = false
)

class PerfilViewModel(
    private val repository: AuthRepository = AuthRepositoryLocal
) : ViewModel() {


    private val _uiState = MutableStateFlow(PerfilUiState(usuario = repository.usuarioActual()))
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun onCerrarSesionClick() {
        repository.cerrarSesion()
        _uiState.update { it.copy(usuario = null, sesionCerrada = true) }
    }
}