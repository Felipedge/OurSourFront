package com.example.oursour.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.AuthRepository
import com.example.oursour.model.AuthRepositoryLocal
import com.example.oursour.model.DatosUsuario
import com.example.oursour.model.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val mostrarPassword: Boolean = false,
    val errorEmail: String? = null,
    val errorPassword: String? = null,
    val errorGeneral: String? = null,
    val cargando: Boolean = false,
    val usuarioLogueado: DatosUsuario? = null
)

class LoginViewModel(
    private val repository: AuthRepository = AuthRepositoryLocal
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(valor: String) =
        _uiState.update { it.copy(email = valor, errorEmail = null, errorGeneral = null) }

    fun onPasswordChange(valor: String) =
        _uiState.update { it.copy(password = valor, errorPassword = null, errorGeneral = null) }

    fun onMostrarPasswordClick() =
        _uiState.update { it.copy(mostrarPassword = !it.mostrarPassword) }

    fun onLoginClick() {
        if (!validar()) return
        val estado = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, errorGeneral = null) }
            repository.login(LoginRequest(estado.email.trim(), estado.password))
                .onSuccess { respuesta ->
                    _uiState.update { it.copy(cargando = false, usuarioLogueado = respuesta.usuario) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(cargando = false, errorGeneral = error.message ?: "No se puede iniciar sesión")
                    }
                }
        }
    }

    fun onNavegacionRealizada() =
        _uiState.update { LoginUiState() }

    private fun validar(): Boolean {
        val s = _uiState.value
        val errorEmail = when {
            s.email.isBlank() -> "Se necesita un Correo"
            !Patterns.EMAIL_ADDRESS.matcher(s.email.trim()).matches() -> "El Correo no tiene un formato válido"
            else -> null
        }
        val errorPassword = if (s.password.isBlank()) "La contraseña es obligatoria" else null
        _uiState.update { it.copy(errorEmail = errorEmail, errorPassword = errorPassword) }
        return errorEmail == null && errorPassword == null
    }
}