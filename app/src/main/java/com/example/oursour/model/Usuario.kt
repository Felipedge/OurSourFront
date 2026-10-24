package com.example.oursour.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.oursour.dao.BaseDatos
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

// ==================== Tabla de Room (@Entity) ====================

@Entity(tableName = "usuario", indices = [Index(value = ["email"], unique = true)])
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val idUsuario: Long = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String,
    val fechaRegistro: String
) {
    fun aDatosUsuario(): DatosUsuario = DatosUsuario(idUsuario, nombre, email, rol, fechaRegistro)
}

// ==================== Repositorio ====================

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

object AuthRepositoryRoom : AuthRepository {

    private val dao get() = BaseDatos.obtener().usuarioDao()

    private var sesion: AuthResponse? = null

    override suspend fun login(req: LoginRequest): Result<AuthResponse> {
        crearCuentasDePrueba()
        val usuario = dao.buscarPorEmail(req.email.trim().lowercase())
        if (usuario == null || usuario.password != req.password) {
            return Result.failure(IllegalArgumentException("Email o contraseña incorrectos"))
        }
        return Result.success(crearSesion(usuario.aDatosUsuario()))
    }

    override suspend fun registrar(req: RegisterRequest): Result<AuthResponse> {
        crearCuentasDePrueba()
        val email = req.email.trim().lowercase()
        if (dao.buscarPorEmail(email) != null) {
            return Result.failure(IllegalStateException("Ya existe una cuenta con ese email"))
        }
        val nuevo = UsuarioEntity(
            nombre = req.nombre.trim(),
            email = email,
            password = req.password,
            rol = "CONSUMIDOR",
            fechaRegistro = LocalDateTime.now().withNano(0).toString()
        )
        val idGenerado = dao.insertar(nuevo)
        return Result.success(crearSesion(nuevo.copy(idUsuario = idGenerado).aDatosUsuario()))
    }

    override fun usuarioActual(): DatosUsuario? = sesion?.usuario

    override fun token(): String? = sesion?.token

    override fun cerrarSesion() {
        sesion = null
    }

    private suspend fun crearCuentasDePrueba() {
        if (dao.contar() == 0) {
            dao.insertar(
                UsuarioEntity(
                    nombre = "Felipe Palma", email = "consumidor@oursour.cl", password = "12345678",
                    rol = "CONSUMIDOR", fechaRegistro = "2026-09-20T10:15:00"
                )
            )
            dao.insertar(
                UsuarioEntity(
                    nombre = "Admin", email = "admin@oursour.cl", password = "12345678",
                    rol = "ADMIN", fechaRegistro = "2026-09-01T09:00:00"
                )
            )
        }
    }

    private fun crearSesion(datos: DatosUsuario): AuthResponse =
        AuthResponse(token = "token-local-${datos.idUsuario}", tipo = "Bearer", usuario = datos)
            .also { sesion = it }
}