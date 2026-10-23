package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel

class DetalleRecetaViewModel : ViewModel(){
    // aqui van los datos de prueba de mas adelante
    val titulo = "Pisco Sour"
    val tipo = "Tipo: COCTEL"
    val ingredientes = "Ingredientes: Pisco" +
            " 35, de limon azucar," +
            " clara de huevoi"
    val pasos = "Pasos: " +
            "Mezclar todo en una" +
            " cocteleria con hielo, y " +
            " agitar y servir "
}