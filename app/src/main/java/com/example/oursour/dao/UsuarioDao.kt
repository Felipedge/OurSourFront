package com.example.oursour.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.oursour.model.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuario WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): UsuarioEntity?

    @Query("SELECT COUNT(*) FROM usuario")
    suspend fun contar(): Int

    @Query("SELECT * FROM usuario ORDER BY idUsuario")
    suspend fun listar(): List<UsuarioEntity>
}