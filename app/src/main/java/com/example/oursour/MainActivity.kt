package com.example.oursour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.oursour.dao.BaseDatos
import com.example.oursour.model.CarroLocal
import com.example.oursour.model.PedidosLocal
import com.example.oursour.navigation.AppNavHost
import com.example.oursour.ui.theme.OurSourTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseDatos.iniciar(applicationContext)

        // recupera el carro y los pedidos guardados en Room
        lifecycleScope.launch {
            CarroLocal.cargar()
            PedidosLocal.cargar()
        }

        enableEdgeToEdge()
        setContent {
            OurSourTheme {
                AppNavHost()
            }
        }
    }
}