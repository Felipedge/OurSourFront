package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.oursour.model.RecetaRepositoryLocal

class DetalleRecetaViewModel : ViewModel() {
    //aqui tenemos la  receta que se eligió en RecetasRecomendadas (puede no existir)
    private val receta = RecetaRepositoryLocal.recetas.firstOrNull {
        it.id == RecetaRepositoryLocal.recetaElegida
    }
    private val idsReceta = receta?.ingredientes ?: emptyList()

    val titulo = receta?.titulo ?: "Receta no encontrada"
    val tipo = receta?.tipo ?: ""
    val pasos = receta?.pasos ?: ""

    // aqui  tenemos  todos los ingredientes de la receta
    val ingredientes = RecetaRepositoryLocal.ingredientes.filter { it.id in idsReceta }

    // aqui tenemos  los que el usuario no marcó
    val faltantes = ingredientes.filter { it.id !in RecetaRepositoryLocal.seleccionActual }
}