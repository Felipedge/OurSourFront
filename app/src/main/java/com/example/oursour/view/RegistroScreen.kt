package com.example.oursour.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegistroScreen(
    nombre: String,
    email: String,
    password: String,
    confirmarPassword: String,
    errorNombre: String?,
    errorEmail: String?,
    errorPassword: String?,
    errorConfirmar: String?,
    errorGeneral: String?,
    cargando: Boolean,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmarPasswordChange: (String) -> Unit,
    onRegistrarClick: () -> Unit,
    onIrALoginClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Crear cuenta",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Guarda tus ingredientes y descubre qué preparar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = onNombreChange,
                        label = { Text("Nombre") },
                        isError = errorNombre != null,
                        supportingText = { errorNombre?.let { Text(it) } },
                        singleLine = true,
                        enabled = !cargando,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = { Text("Correo") },
                        placeholder = { Text("nombre@correo.cl") },
                        isError = errorEmail != null,
                        supportingText = { errorEmail?.let { Text(it) } },
                        singleLine = true,
                        enabled = !cargando,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = { Text("Contraseña") },
                        isError = errorPassword != null,
                        supportingText = { Text(errorPassword ?: "Mínimo 8 caracteres") },
                        singleLine = true,
                        enabled = !cargando,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = confirmarPassword,
                        onValueChange = onConfirmarPasswordChange,
                        label = { Text("Repetir contraseña") },
                        isError = errorConfirmar != null,
                        supportingText = { errorConfirmar?.let { Text(it) } },
                        singleLine = true,
                        enabled = !cargando,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )

                    errorGeneral?.let {
                        Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.fillMaxWidth())
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = onRegistrarClick,
                        enabled = !cargando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        if (cargando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Crear cuenta", style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    TextButton(onClick = onIrALoginClick, enabled = !cargando) {
                        Text("¿Ya tienes cuenta? Inicia sesión")
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroScreenPreview() {
    MaterialTheme {
        RegistroScreen(
            nombre = "", email = "", password = "", confirmarPassword = "",
            errorNombre = null, errorEmail = null, errorPassword = null,
            errorConfirmar = null, errorGeneral = null, cargando = false,
            onNombreChange = {}, onEmailChange = {}, onPasswordChange = {}, onConfirmarPasswordChange = {},
            onRegistrarClick = {}, onIrALoginClick = {}
        )
    }
}