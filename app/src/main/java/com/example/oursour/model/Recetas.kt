package com.example.oursour.model

data class Ingrediente(val id: Long, val nombre: String, val categoria: String)

data class Receta(
    val id: Long,
    val titulo: String,
    val tipo: String,
    val pasos: String,
    val ingredientes: List<Long>
)

data class RecetaRecomendada(
    val receta: Receta,
    val porcentaje: Int,
    val faltantes: List<Ingrediente>
)

object RecetaRepositoryLocal {
    val ingredientes = listOf(
        Ingrediente(1, "Pisco", "DESTILADO"), Ingrediente(2, "Limón", "FRUTA"),
        Ingrediente(3, "Azúcar", "OTRO"), Ingrediente(4, "Clara de huevo", "OTRO"),
        Ingrediente(5, "Ron blanco", "DESTILADO"), Ingrediente(6, "Menta", "OTRO"),
        Ingrediente(7, "Soda", "MIXER"), Ingrediente(8, "Gin", "DESTILADO"),
        Ingrediente(9, "Agua tónica", "MIXER"), Ingrediente(10, "Jugo de naranja", "JUGO")
    )

    val recetas = listOf(
        Receta(1, "Pisco Sour", "COCTEL", "Batir pisco, limón, azúcar y clara con hielo.", listOf(1, 2, 3, 4)),
        Receta(2, "Mojito", "COCTEL", "Machacar menta con azúcar y limón, agregar ron y soda.", listOf(5, 6, 3, 2, 7)),
        Receta(3, "Gin Tonic", "COCTEL", "Gin con hielo y agua tónica.", listOf(8, 9)),
        Receta(4, "Limonada", "MOCKTAIL", "Limón, azúcar y agua con hielo.", listOf(2, 3)),
        Receta(5, "Gin Sour", "COCTEL", "Batir gin, limón, azúcar y clara.", listOf(8, 2, 3, 4)),
        Receta(6, "Naranja Soda", "MOCKTAIL", "Jugo de naranja con soda y hielo.", listOf(10, 7))
    )

    var seleccionActual: Set<Long> = emptySet()   // la guarda SeleccionarIngredientes
    var recetaElegida: Long? = null               // la guarda RecetasRecomendadas

    fun recomendar(): List<RecetaRecomendada> =
        recetas.map { r ->
            val tiene = r.ingredientes.count { it in seleccionActual }
            RecetaRecomendada(
                receta = r,
                porcentaje = tiene * 100 / r.ingredientes.size,
                faltantes = ingredientes.filter { it.id in r.ingredientes && it.id !in seleccionActual }
            )
        }.filter { it.porcentaje > 0 }.sortedByDescending { it.porcentaje }
}