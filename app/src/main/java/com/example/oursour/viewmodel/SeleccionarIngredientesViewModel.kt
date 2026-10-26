package com.example.oursour.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oursour.model.RecetaRepositoryLocal

class SeleccionarIngredientesViewModel : ViewModel() {
    var seleccionados by mutableStateOf(setOf<Long>())
    fun alternar(id: Long) {
        seleccionados = if (id in seleccionados) seleccionados - id else seleccionados + id
    }
    fun guardarSeleccion() {
        RecetaRepositoryLocal.seleccionActual = seleccionados
        RecetaRepositoryLocal.contarBusqueda(seleccionados)
    }
}