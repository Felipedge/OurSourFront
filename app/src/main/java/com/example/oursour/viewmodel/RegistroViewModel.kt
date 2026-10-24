package com.example.oursour.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.AuthRepository
import com.example.oursour.model.AuthRepositoryRoom
import com.example.oursour.model.DatosUsuario
import com.example.oursour.model.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val confirmarPassword: String = "",
    val errorNombre: String? = null,
    val errorEmail: String? = null,
    val errorPassword: String? = null,
    val errorConfirmar: String? = null,
    val errorGeneral: String? = null,
    val cargando: Boolean = false,
    val usuarioRegistrado: DatosUsuario? = null
)

class RegistroViewModel(
    private val repository: AuthRepository = AuthRepositoryRoom
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onNombreChange(valor: String) =
        _uiState.update { it.copy(nombre = valor, errorNombre = null, errorGeneral = null) }

    fun onEmailChange(valor: String) =
        _uiState.update { it.copy(email = valor, errorEmail = null, errorGeneral = null) }

    fun onPasswordChange(valor: String) =
        _uiState.update { it.copy(password = valor, errorPassword = null, errorGeneral = null) }

    fun onConfirmarPasswordChange(valor: String) =
        _uiState.update { it.copy(confirmarPassword = valor, errorConfirmar = null, errorGeneral = null) }

    fun onRegistrarClick() {
        if (!validar()) return
        val s = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, errorGeneral = null) }
            repository.registrar(RegisterRequest(s.nombre.trim(), s.email.trim(), s.password))
                .onSuccess { respuesta ->
                    _uiState.update { it.copy(cargando = false, usuarioRegistrado = respuesta.usuario) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(cargando = false, errorGeneral = error.message ?: "No se pudo crear la cuenta")
                    }
                }
        }
    }


    fun onNavegacionRealizada() =
        _uiState.update { RegistroUiState() }

    private fun validar(): Boolean {
        val s = _uiState.value
        val errorNombre = when {
            s.nombre.isBlank() -> "El nombre es obligatorio"
            s.nombre.length > 100 -> "El nombre no puede superar 100 caracteres"
            else -> null
        }
        val errorEmail = when {
            s.email.isBlank() -> "El email es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(s.email.trim()).matches() -> "El email no tiene un formato válido"
            s.email.length > 150 -> "El email no puede superar 150 caracteres"
            else -> null
        }
        val errorPassword = when {
            s.password.isBlank() -> "La contraseña es obligatoria"
            s.password.length !in 8..60 -> "La contraseña debe tener entre 8 y 60 caracteres"
            else -> null
        }
        val errorConfirmar = if (s.confirmarPassword != s.password) "Las contraseñas no coinciden" else null

        _uiState.update {
            it.copy(
                errorNombre = errorNombre,
                errorEmail = errorEmail,
                errorPassword = errorPassword,
                errorConfirmar = errorConfirmar
            )
        }
        return listOf(errorNombre, errorEmail, errorPassword, errorConfirmar).all { it == null }
    }
}