package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.RecetaRepositoryLocal

class RecetasRecomendadasViewModel : ViewModel() {
    val recomendadas = RecetaRepositoryLocal.recomendar()
    fun elegirReceta(id: Long) {
        RecetaRepositoryLocal.recetaElegida = id
    }
}