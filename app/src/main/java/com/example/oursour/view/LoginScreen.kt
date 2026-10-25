package com.example.oursour.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oursour.ui.theme.FuenteLogo


@Composable
fun LoginScreen(
    email: String,
    password: String,
    mostrarPassword: Boolean,
    errorEmail: String?,
    errorPassword: String?,
    errorGeneral: String?,
    cargando: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarPasswordClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegistroClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Our Sour",
                style = MaterialTheme.typography.displaySmall,
                fontFamily = FuenteLogo,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "COCTELERÍA EN CASA",
                style = MaterialTheme.typography.labelMedium,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(40.dp))

            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Correo") },
                placeholder = { Text("nombre@correo.cl") },
                isError = errorEmail != null,
                supportingText = { errorEmail?.let { Text(it) } },
                singleLine = true,
                enabled = !cargando,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Contraseña") },
                isError = errorPassword != null,
                supportingText = { errorPassword?.let { Text(it) } },
                singleLine = true,
                enabled = !cargando,
                visualTransformation = if (mostrarPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = onMostrarPasswordClick) {
                        Text(if (mostrarPassword) "Ocultar" else "Mostrar")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { onLoginClick() }),
                modifier = Modifier.fillMaxWidth()
            )

            errorGeneral?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onLoginClick,
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
                    Text("Iniciar sesión", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = onRegistroClick, enabled = !cargando) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Login vacío")
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            email = "",
            password = "",
            mostrarPassword = false,
            errorEmail = null,
            errorPassword = null,
            errorGeneral = null,
            cargando = false,
            onEmailChange = {},
            onPasswordChange = {},
            onMostrarPasswordClick = {},
            onLoginClick = {},
            onRegistroClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Login con errores")
@Composable
fun LoginScreenErroresPreview() {
    MaterialTheme {
        LoginScreen(
            email = "camila@",
            password = "123",
            mostrarPassword = true,
            errorEmail = "Correo no válido",
            errorPassword = "Mínimo 8 caracteres",
            errorGeneral = null,
            cargando = false,
            onEmailChange = {},
            onPasswordChange = {},
            onMostrarPasswordClick = {},
            onLoginClick = {},
            onRegistroClick = {}
        )
    }
}