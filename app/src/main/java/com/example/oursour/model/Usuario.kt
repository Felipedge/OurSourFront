package com.example.oursour.model


data class AuthResponse(
    val token: String,
    val tipo: String,
    val usuario: DatosUsuario
)
data class DatosUsuario(
    val idUsuario: Long,
    val nombre: String,
    val email: String,
    val rol: String,
    val fechaRegistro: String
) {
    val esAdmin: Boolean get() = rol == "ADMIN"
}


data class LoginRequest(
    val email: String,
    val password: String
)
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)