package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.Ingrediente
import com.example.oursour.model.RecetaRepositoryLocal

class IngredienteFrecuenteViewModel: ViewModel(){
    val titulo = "Ingredientes frecuentes"

    // aqui tenemos la lista de los ingredientes buscados, del mas buscado al menos buscado
    val listaFrecuentes: List<Ingrediente>
        get() = RecetaRepositoryLocal.ingredientes
            .filter { veces(it.id) > 0 }
            .sortedByDescending { veces(it.id) }

    // aqui tenemos cuantas veces se ha buscado un ingrediente
    fun veces(id: Long): Int {
        return RecetaRepositoryLocal.busquedas[id] ?: 0
    }
}