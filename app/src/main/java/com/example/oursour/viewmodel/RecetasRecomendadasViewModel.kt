package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.RecetaRepositoryLocal

class RecetasRecomendadasViewModel : ViewModel() {
    // aqui tenemos la recetas ordenadas segun los ingredientes marcados
    val recomendadas = RecetaRepositoryLocal.recomendar()

    // aqui guardamos la receta que se eligio
    fun elegirReceta(id: Long) {
        RecetaRepositoryLocal.recetaElegida = id
    }
}