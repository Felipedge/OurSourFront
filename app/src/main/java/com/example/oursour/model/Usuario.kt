package com.example.oursour.model

import kotlinx.coroutines.delay
import java.time.LocalDateTime

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

interface AuthRepository {
    suspend fun login(req: LoginRequest): Result<AuthResponse>
    suspend fun registrar(req: RegisterRequest): Result<AuthResponse>
    fun usuarioActual(): DatosUsuario?
    fun token(): String?
    fun cerrarSesion()
}


object AuthRepositoryLocal : AuthRepository {

    private data class CuentaLocal(val password: String, val datos: DatosUsuario)

    private val cuentas = mutableMapOf(
        "consumidor@oursour.cl" to CuentaLocal(
            "12345678",
            DatosUsuario(1, "Felipe Palma", "consumidor@oursour.cl", "CONSUMIDOR", "2026-09-20T10:15:00")
        ),
        "admin@oursour.cl" to CuentaLocal(
            "12345678",
            DatosUsuario(2, "Admin", "admin@oursour.cl", "ADMIN", "2026-09-01T09:00:00")
        )
    )

    private var sesion: AuthResponse? = null

    override suspend fun login(req: LoginRequest): Result<AuthResponse> {
        delay(600) // simula la red
        val cuenta = cuentas[req.email.trim().lowercase()]
        if (cuenta == null || cuenta.password != req.password) {
            return Result.failure(IllegalArgumentException("Email o contraseña incorrectos"))
        }
        return Result.success(crearSesion(cuenta.datos))
    }

    override suspend fun registrar(req: RegisterRequest): Result<AuthResponse> {
        delay(600)
        val email = req.email.trim().lowercase()
        if (cuentas.containsKey(email)) {
            return Result.failure(IllegalStateException("Ya existe una cuenta con ese email"))
        }
        val nuevo = DatosUsuario(
            idUsuario = cuentas.values.maxOf { it.datos.idUsuario } + 1,
            nombre = req.nombre.trim(),
            email = email,
            rol = "CONSUMIDOR",
            fechaRegistro = LocalDateTime.now().withNano(0).toString()
        )
        cuentas[email] = CuentaLocal(req.password, nuevo)
        return Result.success(crearSesion(nuevo))
    }

    override fun usuarioActual(): DatosUsuario? = sesion?.usuario

    override fun token(): String? = sesion?.token

    override fun cerrarSesion() {
        sesion = null
    }
    private fun crearSesion(datos: DatosUsuario): AuthResponse =
        AuthResponse(token = "token-local-${datos.idUsuario}", tipo = "Bearer", usuario = datos)
            .also { sesion = it }
}