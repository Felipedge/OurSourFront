package com.example.oursour.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oursour.model.CarroLocal
import com.example.oursour.model.PedidosLocal
import com.example.oursour.model.Referencia

class DespachoViewModel: ViewModel() {
    var calle by mutableStateOf("")
    var numero by mutableStateOf("")
    var comuna by mutableStateOf("")
    var ciudad by mutableStateOf("")
    var referencia by mutableStateOf("")
    var textoVisible by mutableStateOf(false)

    val costoDespacho: Double
        get() = CarroLocal.costoDespacho

    fun cambiarCalle(nuevaCalle: String) {
        calle = nuevaCalle
    }

    fun cambiarNumero(nuevoNumero: String) {
        numero = nuevoNumero
    }

    fun cambiarComuna(nuevaComuna: String) {
        comuna = nuevaComuna
    }

    fun cambiarCiudad(nuevaCiudad: String) {
        ciudad = nuevaCiudad
    }

    fun cambiarReferencia(nuevaReferencia: String) {
        referencia = nuevaReferencia
    }

    fun guardar(): Boolean {
        var validador = true
        if (calle == "") {
            validador = false
        }

        if (numero == "") {
            validador = false
        }

        if (comuna == "") {
            validador = false
        }
        if (ciudad == "") {
            validador = false
        }
        if (validador) {
            PedidosLocal.direccionActual = "$calle $numero, $comuna, $ciudad"
        }
        return validador
    }
}