package com.example.oursour.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oursour.model.RecetaRepositoryLocal

class IngredienteFrecuenteViewModel: ViewModel(){
    val titulo = "Ingredientes frecuentes"

    // aqui tenemos el id de los ingredientes frecuentes que marcamos
    var frecuentes by mutableStateOf(RecetaRepositoryLocal.frecuentes)

    // aqui tenemos que  marca o desmarca un ingrediente frecuente y lo guarda
    fun alternar(id: Long) {
        frecuentes = if (id in frecuentes) frecuentes - id else frecuentes + id
        RecetaRepositoryLocal.frecuentes = frecuentes
    }

    // aqui usamos los frecuentes como la selección para RecetasRecomendadas
    fun guardarSeleccion() {
        RecetaRepositoryLocal.seleccionActual = frecuentes
    }
}