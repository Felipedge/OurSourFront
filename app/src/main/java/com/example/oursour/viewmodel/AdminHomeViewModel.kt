package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.AuthRepository
import com.example.oursour.model.AuthRepositoryRoom
import com.example.oursour.model.MetricasRepository
import com.example.oursour.model.MetricasRepositoryRoom
import com.example.oursour.model.ResumenMetricas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AdminHomeUiState(
    val nombreAdmin: String = "",
    val cargando: Boolean = true,
    val resumen: ResumenMetricas? = null,
    val sesionCerrada: Boolean = false
)

class AdminHomeViewModel(
    private val authRepository: AuthRepository = AuthRepositoryRoom,
    private val metricasRepository: MetricasRepository = MetricasRepositoryRoom
) : ViewModel() {


    private val _uiState = MutableStateFlow(
        AdminHomeUiState(nombreAdmin = authRepository.usuarioActual()?.nombre ?: "Administrador")
    )
    val uiState: StateFlow<AdminHomeUiState> = _uiState.asStateFlow()

    init {
        cargarResumen()
    }

    fun cargarResumen() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            val resumen = metricasRepository.resumen().getOrNull()
            _uiState.update { it.copy(cargando = false, resumen = resumen) }
        }
    }

    fun onCerrarSesionClick() {
        authRepository.cerrarSesion()
        _uiState.update { it.copy(sesionCerrada = true) }
    }
}