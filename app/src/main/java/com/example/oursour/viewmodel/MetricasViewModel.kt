package com.example.oursour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oursour.model.BusquedasDia
import com.example.oursour.model.Combinacion
import com.example.oursour.model.Conteo
import com.example.oursour.model.MetricasRepository
import com.example.oursour.model.MetricasRepositoryRoom
import com.example.oursour.model.ResumenMetricas
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MetricasUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val dias: Int = 30,
    val resumen: ResumenMetricas? = null,
    val busquedasPorDia: List<BusquedasDia> = emptyList(),
    val ingredientesTop: List<Conteo> = emptyList(),
    val recetasTop: List<Conteo> = emptyList(),
    val productosTop: List<Conteo> = emptyList(),
    val combinaciones: List<Combinacion> = emptyList()
)

class MetricasViewModel(
    private val repository: MetricasRepository = MetricasRepositoryRoom
) : ViewModel() {

    private val _uiState = MutableStateFlow(MetricasUiState())
    val uiState: StateFlow<MetricasUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }


    fun cargar() {
        val dias = _uiState.value.dias
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, error = null) }

            val resumen = async { repository.resumen() }
            val tendencia = async { repository.tendencias(dias) }
            val ingredientes = async { repository.ingredientesMasBuscados(5) }
            val recetas = async { repository.recetasMasConsultadas(5) }
            val productos = async { repository.productosMasVendidos(5) }
            val combinaciones = async { repository.combinacionesFrecuentes(5) }

            try {
                _uiState.update {
                    it.copy(
                        cargando = false,
                        resumen = resumen.await().getOrThrow(),
                        busquedasPorDia = tendencia.await().getOrThrow().busquedasPorDia,
                        ingredientesTop = ingredientes.await().getOrThrow(),
                        recetasTop = recetas.await().getOrThrow(),
                        productosTop = productos.await().getOrThrow(),
                        combinaciones = combinaciones.await().getOrThrow()
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(cargando = false, error = e.message ?: "No se pudieron cargar las métricas") }
            }
        }
    }


    fun onDiasChange(nuevosDias: Int) {
        if (nuevosDias == _uiState.value.dias) return
        _uiState.update { it.copy(dias = nuevosDias) }

        viewModelScope.launch {
            repository.tendencias(nuevosDias)
                .onSuccess { t -> _uiState.update { it.copy(busquedasPorDia = t.busquedasPorDia) } }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }
}