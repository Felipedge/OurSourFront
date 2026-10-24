package com.example.oursour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.oursour.dao.BaseDatos
import com.example.oursour.navigation.AppNavHost
import com.example.oursour.ui.theme.OurSourTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseDatos.iniciar(applicationContext)
        enableEdgeToEdge()
        setContent {
            OurSourTheme {
                AppNavHost()
            }
        }
    }
}