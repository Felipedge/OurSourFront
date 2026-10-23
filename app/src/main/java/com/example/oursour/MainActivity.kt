package com.example.oursour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oursour.ui.theme.OurSourTheme
import com.example.oursour.view.DellePedidoScreen
import com.example.oursour.view.DespachoScreen
import com.example.oursour.view.DetalleRecetaScreen
import com.example.oursour.view.LoginScreen
import com.example.oursour.view.MisPedidosScreen
import com.example.oursour.view.PagoScreen
import com.example.oursour.view.ResultadoPagoScreen
import com.example.oursour.view.ResumenCompraScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OurSourTheme {
                MisPedidosScreen()
                //DetalleRecetaScreen()
                //DellePedidoScreen()
                //ResumenCompraScreen()
                //DespachoScreen()
                //ResultadoPagoScreen()
                //PagoScreen()
                //LoginScreen (
                   // email = "",
                    //password = "",
                    //mostrarPassword = false,
                    //errorEmail = null,
                    //errorPassword = null,
                    //errorGeneral = null,
                    //cargando = false,
                    //onEmailChange = {},
                    //onPasswordChange = {},
                    //onMostrarPasswordClick = {},
                    //onLoginClick = {},
                    //onRegistroClick = {}

                //)

            }
        }
    }
}