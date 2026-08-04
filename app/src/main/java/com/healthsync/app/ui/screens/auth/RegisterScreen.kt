package com.healthsync.app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.healthsync.app.ui.components.AppCard
import com.healthsync.app.ui.components.BrandLogo
import com.healthsync.app.ui.components.PrimaryButton
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorBorder
import com.healthsync.app.ui.theme.ColorErrorBg
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorInputBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onLogin: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = ColorTextSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        BrandLogo(
            name = "Dialitech",
            tagline = "Fortaleciendo tu camino en la diálisis con claridad."
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Regístrate",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTextPrimary
                )
                Text(
                    text = "Completa tus datos para crear una cuenta.",
                    fontSize = 14.sp,
                    color = ColorTextSecondary,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Nombre Completo",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.fullName,
                    onValueChange = { viewModel.onFullNameChanged(it) },
                    placeholder = { Text("Juan Pérez", color = ColorTextMuted) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Nombre", tint = ColorTextMuted)
                    },
                    isError = state.fullNameError != null,
                    supportingText = state.fullNameError?.let { { Text(it, color = Color(0xFFDC2626)) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = ColorInputBg,
                        unfocusedBorderColor = ColorBorder,
                        focusedBorderColor = ColorPrimary,
                        cursorColor = ColorPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Correo Electrónico",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    placeholder = { Text("nombre@ejemplo.com", color = ColorTextMuted) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Correo", tint = ColorTextMuted)
                    },
                    isError = state.emailError != null,
                    supportingText = state.emailError?.let { { Text(it, color = Color(0xFFDC2626)) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = ColorInputBg,
                        unfocusedBorderColor = ColorBorder,
                        focusedBorderColor = ColorPrimary,
                        cursorColor = ColorPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Contraseña",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = { Text("••••••••", color = ColorTextMuted) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Contraseña", tint = ColorTextMuted)
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                            Icon(
                                imageVector = if (state.showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (state.showPassword) "Ocultar" else "Mostrar",
                                tint = ColorTextMuted
                            )
                        }
                    },
                    visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = state.passwordError != null,
                    supportingText = state.passwordError?.let { { Text(it, color = Color(0xFFDC2626)) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = ColorInputBg,
                        unfocusedBorderColor = ColorBorder,
                        focusedBorderColor = ColorPrimary,
                        cursorColor = ColorPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Confirmar Contraseña",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    placeholder = { Text("••••••••", color = ColorTextMuted) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Confirmar", tint = ColorTextMuted)
                    },
                    isError = state.confirmPasswordError != null,
                    supportingText = state.confirmPasswordError?.let { { Text(it, color = Color(0xFFDC2626)) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = ColorInputBg,
                        unfocusedBorderColor = ColorBorder,
                        focusedBorderColor = ColorPrimary,
                        cursorColor = ColorPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.networkError != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ColorErrorBg, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.networkError,
                            fontSize = 13.sp,
                            color = ColorErrorRed
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                PrimaryButton(
                    text = "Crear Cuenta",
                    onClick = { viewModel.register(onRegisterSuccess) },
                    loading = state.isSubmitting,
                    enabled = !state.isSubmitting
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿Ya tienes una cuenta? ",
                fontSize = 13.sp,
                color = ColorTextSecondary
            )
            Text(
                text = "Iniciar sesión",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
                modifier = Modifier.clickable { onLogin() }
            )
        }
    }
}
