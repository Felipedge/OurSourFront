package com.example.oursour.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oursour.model.RecetaRepositoryLocal

class SeleccionarIngredientesViewModel : ViewModel() {
    // aqui tenemos el id de los ingredietes que marcamos
    var seleccionados by mutableStateOf(setOf<Long>())

    // marca o desmarca un ingrediente
    fun alternar(id: Long) {
        seleccionados = if (id in seleccionados) seleccionados - id else seleccionados + id
    }

    // aqui  guardamos la selección para que lo use RecetasRecomendadas
    // y contamos la busqueda para los ingredientes frecuentes
    fun guardarSeleccion() {
        RecetaRepositoryLocal.seleccionActual = seleccionados
        RecetaRepositoryLocal.contarBusqueda(seleccionados)
    }
}