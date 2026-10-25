package com.example.oursour.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.oursour.model.CarroLocal
import com.example.oursour.view.*
import com.example.oursour.viewmodel.*
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val volver: () -> Unit = { navController.popBackStack() }

    NavHost(navController = navController, startDestination = Rutas.LOGIN) {

        // ==================== SESIÓN ====================

        composable(Rutas.LOGIN) {
            val vm: LoginViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(estado.usuarioLogueado) {
                val usuario = estado.usuarioLogueado ?: return@LaunchedEffect
                val destino = if (usuario.esAdmin) Rutas.ADMIN_HOME else Rutas.HOME
                navController.navigate(destino) { popUpTo(Rutas.LOGIN) { inclusive = true } }
                vm.onNavegacionRealizada()
            }

            LoginScreen(
                email = estado.email,
                password = estado.password,
                mostrarPassword = estado.mostrarPassword,
                errorEmail = estado.errorEmail,
                errorPassword = estado.errorPassword,
                errorGeneral = estado.errorGeneral,
                cargando = estado.cargando,
                onEmailChange = { vm.onEmailChange(it) },
                onPasswordChange = { vm.onPasswordChange(it) },
                onMostrarPasswordClick = { vm.onMostrarPasswordClick() },
                onLoginClick = { vm.onLoginClick() },
                onRegistroClick = { navController.navigate(Rutas.REGISTRO) }
            )
        }

        composable(Rutas.REGISTRO) {
            val vm: RegistroViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(estado.usuarioRegistrado) {
                if (estado.usuarioRegistrado != null) {
                    navController.navigate(Rutas.HOME) { popUpTo(Rutas.LOGIN) { inclusive = true } }
                    vm.onNavegacionRealizada()
                }
            }

            RegistroScreen(
                nombre = estado.nombre,
                email = estado.email,
                password = estado.password,
                confirmarPassword = estado.confirmarPassword,
                errorNombre = estado.errorNombre,
                errorEmail = estado.errorEmail,
                errorPassword = estado.errorPassword,
                errorConfirmar = estado.errorConfirmar,
                errorGeneral = estado.errorGeneral,
                cargando = estado.cargando,
                onNombreChange = { vm.onNombreChange(it) },
                onEmailChange = { vm.onEmailChange(it) },
                onPasswordChange = { vm.onPasswordChange(it) },
                onConfirmarPasswordChange = { vm.onConfirmarPasswordChange(it) },
                onRegistrarClick = { vm.onRegistrarClick() },
                onIrALoginClick = volver
            )
        }

        // ==================== CONSUMIDOR (Felipe) ====================

        composable(Rutas.HOME) {
            val vm: PerfilViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                nombreUsuario = estado.usuario?.nombre ?: "",
                cantidadEnCarro = CarroLocal.cantidadTotal,
                onBuscarRecetasClick = { navController.navigate(Rutas.SELECCIONAR_INGREDIENTES) },
                onIngredientesFrecuentesClick = { navController.navigate(Rutas.INGREDIENTES_FRECUENTES) },
                onCatalogoClick = { navController.navigate(Rutas.CATALOGO) },
                onCarroClick = { navController.navigate(Rutas.CARRITO) },
                onMisPedidosClick = { navController.navigate(Rutas.MIS_PEDIDOS) },
                onPerfilClick = { navController.navigate(Rutas.PERFIL) }
            )
        }

        composable(Rutas.PERFIL) {
            val vm: PerfilViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(estado.sesionCerrada) {
                if (estado.sesionCerrada) irAlLogin(navController)
            }

            PerfilScreen(
                usuario = estado.usuario,
                onMisPedidosClick = { navController.navigate(Rutas.MIS_PEDIDOS) },
                onIngredientesFrecuentesClick = { navController.navigate(Rutas.INGREDIENTES_FRECUENTES) },
                onCerrarSesionClick = { vm.onCerrarSesionClick() },
                onVolver = volver
            )
        }

        composable(Rutas.CATALOGO) {
            val vm: CatalogoViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            val lectorQr = rememberLauncherForActivityResult(ScanContract()) { resultado ->
                vm.onQrLeido(resultado.contents)
            }

            LaunchedEffect(estado.idProductoQr) {
                val id = estado.idProductoQr ?: return@LaunchedEffect
                navController.navigate(Rutas.detalleProducto(id))
                vm.onQrNavegado()
            }

            CatalogoScreen(
                cargando = estado.cargando,
                error = estado.error,
                busqueda = estado.busqueda,
                productos = estado.productos,
                cantidadEnCarro = CarroLocal.cantidadTotal,
                mensajeQr = estado.mensajeQr,
                onBusquedaChange = { vm.onBusquedaChange(it) },
                onProductoClick = { id -> navController.navigate(Rutas.detalleProducto(id)) },
                onAgregarAlCarroClick = { id -> vm.onAgregarAlCarroClick(id) },
                onCarroClick = { navController.navigate(Rutas.CARRITO) },
                onEscanearQrClick = { lectorQr.launch(opcionesQr()) },
                onVolver = volver,
                onReintentar = { vm.cargar() }
            )
        }

        composable(
            route = Rutas.DETALLE_PRODUCTO,
            arguments = listOf(navArgument("idProducto") { type = NavType.LongType })
        ) { entrada ->
            val idProducto = entrada.arguments?.getLong("idProducto") ?: 0L
            val vm: DetalleProductoViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(idProducto) { vm.cargar(idProducto) }

            LaunchedEffect(estado.agregadoAlCarro) {
                if (estado.agregadoAlCarro) {
                    vm.onAvisoMostrado()
                    navController.navigate(Rutas.CARRITO)
                }
            }

            DetalleProductoScreen(
                cargando = estado.cargando,
                error = estado.error,
                producto = estado.producto,
                cantidad = estado.cantidad,
                agregando = estado.agregando,
                onCantidadMenos = { vm.onCantidadMenos() },
                onCantidadMas = { vm.onCantidadMas() },
                onAgregarAlCarroClick = { vm.onAgregarAlCarroClick() },
                onVolver = volver,
                onReintentar = { vm.onReintentar() }
            )
        }

        // ==================== ADMIN (Felipe) ====================

        composable(Rutas.ADMIN_HOME) {
            val vm: AdminHomeViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) { vm.cargarResumen() }

            LaunchedEffect(estado.sesionCerrada) {
                if (estado.sesionCerrada) irAlLogin(navController)
            }

            AdminHomeScreen(
                nombreAdmin = estado.nombreAdmin,
                cargando = estado.cargando,
                resumen = estado.resumen,
                onGestionarCatalogoClick = { navController.navigate(Rutas.ADMIN_PRODUCTOS) },
                onVerMetricasClick = { navController.navigate(Rutas.METRICAS) },
                onCerrarSesionClick = { vm.onCerrarSesionClick() }
            )
        }

        composable(Rutas.ADMIN_PRODUCTOS) {
            val vm: AdminProductosViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) { vm.cargar() }

            AdminProductosScreen(
                cargando = estado.cargando,
                error = estado.error,
                productos = estado.productos,
                productoAEliminar = estado.productoAEliminar,
                onNuevoClick = { navController.navigate(Rutas.productoForm()) },
                onEditarClick = { id -> navController.navigate(Rutas.productoForm(id)) },
                onEliminarClick = { id -> vm.onEliminarClick(id) },
                onActivarClick = { id -> vm.onActivarClick(id) },
                onConfirmarEliminarClick = { vm.onConfirmarEliminarClick() },
                onCancelarEliminarClick = { vm.onCancelarEliminarClick() },
                onVolver = volver,
                onReintentar = { vm.cargar() }
            )
        }

        composable(
            route = Rutas.PRODUCTO_FORM,
            arguments = listOf(navArgument("idProducto") {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) { entrada ->
            val id = entrada.arguments?.getLong("idProducto") ?: -1L
            val idProducto = if (id == -1L) null else id
            val vm: ProductoFormViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(idProducto) { vm.iniciar(idProducto) }

            LaunchedEffect(estado.guardadoOk) {
                if (estado.guardadoOk) navController.popBackStack()
            }

            ProductoFormScreen(
                esEdicion = estado.esEdicion,
                cargando = estado.cargando,
                nombre = estado.nombre,
                descripcion = estado.descripcion,
                precio = estado.precio,
                stock = estado.stock,
                errorNombre = estado.errorNombre,
                errorDescripcion = estado.errorDescripcion,
                errorPrecio = estado.errorPrecio,
                errorStock = estado.errorStock,
                errorGeneral = estado.errorGeneral,
                guardando = estado.guardando,
                onNombreChange = { vm.onNombreChange(it) },
                onDescripcionChange = { vm.onDescripcionChange(it) },
                onPrecioChange = { vm.onPrecioChange(it) },
                onStockChange = { vm.onStockChange(it) },
                onGuardarClick = { vm.onGuardarClick() },
                onVolver = volver
            )
        }

        composable(Rutas.METRICAS) {
            val vm: MetricasViewModel = viewModel()
            val estado by vm.uiState.collectAsStateWithLifecycle()

            MetricasScreen(
                cargando = estado.cargando,
                error = estado.error,
                dias = estado.dias,
                resumen = estado.resumen,
                busquedasPorDia = estado.busquedasPorDia,
                ingredientesTop = estado.ingredientesTop,
                recetasTop = estado.recetasTop,
                productosTop = estado.productosTop,
                combinaciones = estado.combinaciones,
                onDiasChange = { vm.onDiasChange(it) },
                onVolver = volver,
                onReintentar = { vm.cargar() }
            )
        }

        // ==================== RECETAS Y COMPRA (Andrés) ====================
        composable(Rutas.SELECCIONAR_INGREDIENTES) {
            SeleccionarIngredientesScreen(
                onVerRecetasClick = { navController.navigate(Rutas.RECETAS_RECOMENDADAS) },
                onVolver = volver
            )
        }

        composable(Rutas.INGREDIENTES_FRECUENTES) {
            IngredienteFrecuenteScreen(
                onVerRecetasClick = { navController.navigate(Rutas.RECETAS_RECOMENDADAS) },
                onVolver = volver
            )
        }

        composable(Rutas.RECETAS_RECOMENDADAS) {
            RecetasRecomendadasScreen(
                onVerDetalleClick = { navController.navigate(Rutas.DETALLE_RECETA) },
                onVolver = volver
            )
        }

        composable(Rutas.DETALLE_RECETA) {
            DetalleRecetaScreen(onVolver = volver)
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                onContinuarClick = { navController.navigate(Rutas.DESPACHO) },
                onSeguirComprandoClick = {
                    if (!navController.popBackStack(Rutas.CATALOGO, inclusive = false)) {
                        navController.navigate(Rutas.CATALOGO)
                    }
                },
                onVolver = volver
            )
        }

        composable(Rutas.DESPACHO) {
            DespachoScreen(
                onDireccionGuardada = { navController.navigate(Rutas.RESUMEN_COMPRA) },
                onVolver = volver
            )
        }

        composable(Rutas.RESUMEN_COMPRA) {
            ResumenCompraScreen(
                onIrAPagarClick = { navController.navigate(Rutas.PAGO) },
                onVolver = volver
            )
        }

        composable(Rutas.PAGO) {
            PagoScreen(
                onPagoRealizado = {
                    navController.navigate(Rutas.RESULTADO_PAGO) {
                        popUpTo(Rutas.CARRITO) { inclusive = true }
                    }
                },
                onVolver = volver
            )
        }

        composable(Rutas.RESULTADO_PAGO) {
            ResultadoPagoScreen(
                onVolverAlInicioClick = { navController.popBackStack(Rutas.HOME, inclusive = false) }
            )
        }

        composable(Rutas.MIS_PEDIDOS) {
            MisPedidosScreen(
                onVerDetalleClick = { navController.navigate(Rutas.DETALLE_PEDIDO) },
                onVolver = volver
            )
        }

        composable(Rutas.DETALLE_PEDIDO) {
            DetallePedidoScreen(onVolver = volver)
        }
    }
}

private fun irAlLogin(navController: NavHostController) {
    navController.navigate(Rutas.LOGIN) {
        popUpTo(navController.graph.id) { inclusive = true }
    }
}

private fun opcionesQr() = ScanOptions().apply {
    setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    setPrompt("Apunta al código QR de la botella")
    setBeepEnabled(false)
    setOrientationLocked(false)
}