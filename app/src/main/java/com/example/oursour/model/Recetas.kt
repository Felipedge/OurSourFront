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
        // los 10 productos del catálogo (mismos nombres e ids)
        Ingrediente(1, "Pisco 35°", "DESTILADO"), Ingrediente(2, "Ron blanco", "DESTILADO"),
        Ingrediente(3, "Ron dorado", "DESTILADO"), Ingrediente(4, "Vodka", "DESTILADO"),
        Ingrediente(5, "Gin", "DESTILADO"), Ingrediente(6, "Tequila blanco", "DESTILADO"),
        Ingrediente(7, "Whisky", "DESTILADO"), Ingrediente(8, "Licor de naranja", "LICOR"),
        Ingrediente(9, "Vermut rojo", "LICOR"), Ingrediente(10, "Licor de café", "LICOR"),
        // ingredientes que no se venden en el catálogo
        Ingrediente(11, "Limón", "FRUTA"), Ingrediente(12, "Azúcar", "OTRO"),
        Ingrediente(13, "Clara de huevo", "OTRO"), Ingrediente(14, "Menta", "OTRO"),
        Ingrediente(15, "Soda", "MIXER"), Ingrediente(16, "Agua tónica", "MIXER"),
        Ingrediente(17, "Jugo de naranja", "JUGO")
    )

    val recetas = listOf(
        Receta(1, "Pisco Sour", "COCTEL", "Batir pisco, limón, azúcar y clara con hielo.", listOf(1, 11, 12, 13)),
        Receta(2, "Mojito", "COCTEL", "Machacar menta con azúcar y limón, agregar ron y soda.", listOf(2, 14, 12, 11, 15)),
        Receta(3, "Gin Tonic", "COCTEL", "Gin con hielo y agua tónica.", listOf(5, 16)),
        Receta(4, "Limonada", "MOCKTAIL", "Limón, azúcar y agua con hielo.", listOf(11, 12)),
        Receta(5, "Gin Sour", "COCTEL", "Batir gin, limón, azúcar y clara.", listOf(5, 11, 12, 13)),
        Receta(6, "Naranja Soda", "MOCKTAIL", "Jugo de naranja con soda y hielo.", listOf(17, 15))
    )

    var seleccionActual: Set<Long> = emptySet()   // la guarda SeleccionarIngredientes
    var recetaElegida: Long? = null //aqui tenemos que  guarda RecetasRecomendadas

    var frecuentes: Set<Long> = emptySet()


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