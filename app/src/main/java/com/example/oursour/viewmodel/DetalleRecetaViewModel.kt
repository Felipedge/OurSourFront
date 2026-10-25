package com.example.oursour.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.CarroLocal
import com.example.oursour.model.Ingrediente
import com.example.oursour.model.ProductoRepositoryLocal
import com.example.oursour.model.RecetaRepositoryLocal
import kotlinx.coroutines.launch

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

    //aqui buscamos el  mensaje que se muestra después de agregar un producto al carro
    var mensaje by mutableStateOf("")

    //aqui buscamos los destilados y licores se venden en el catálogo, el resto no
    fun seVende(ingrediente: Ingrediente): Boolean {
        var respuesta = false
        if (ingrediente.categoria == "DESTILADO" || ingrediente.categoria == "LICOR") {
            respuesta = true
        }
        return respuesta
    }

    //aqui  buscamos en el catálogo un producto con el nombre del ingrediente y lo agrega al carro
    fun agregarAlCarro(ingrediente: Ingrediente) {
        viewModelScope.launch {
            ProductoRepositoryLocal.listar()
                .onSuccess { lista ->
                    val producto = lista.firstOrNull {
                        it.nombreComercial.contains(ingrediente.nombre, ignoreCase = true) && it.hayStock
                    }
                    if (producto == null) {
                        mensaje = "No hay stock disponible para " + ingrediente.nombre
                    } else {
                        CarroLocal.agregar(producto)
                        mensaje = producto.nombreComercial + " agregado al carro"
                    }
                }
                .onFailure {
                    mensaje = "No se pudo cargar el catalogo"
                }
        }
    }
}