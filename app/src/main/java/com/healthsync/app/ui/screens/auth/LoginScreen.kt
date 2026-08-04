package com.healthsync.app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.healthsync.app.ui.theme.ColorInputBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onRegister: () -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
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
        // Barra superior
        Text(
            text = "Iniciar Sesión",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = ColorTextSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // BrandLogo
        BrandLogo(
            name = "Dialitech",
            tagline = "Fortaleciendo tu camino en la diálisis con claridad."
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta del formulario
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Encabezado
                Text(
                    text = "Bienvenido de nuevo",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTextPrimary
                )
                Text(
                    text = "Por favor ingresa tus datos para iniciar sesión.",
                    fontSize = 14.sp,
                    color = ColorTextSecondary,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Campo Email
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

                // Contraseña
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Contraseña",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF374151)
                    )
                    TextButton(onClick = onForgotPassword) {
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorPrimary
                        )
                    }
                }
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
                                contentDescription = if (state.showPassword) "Ocultar contraseña" else "Mostrar contraseña",
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

                Spacer(modifier = Modifier.height(8.dp))

                // Checkbox Recordar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { viewModel.onRememberMeChanged(!state.rememberMe) }
                ) {
                    Checkbox(
                        checked = state.rememberMe,
                        onCheckedChange = { viewModel.onRememberMeChanged(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = ColorPrimary,
                            uncheckedColor = ColorBorder
                        )
                    )
                    Text(
                        text = "Recordar por 30 días",
                        fontSize = 14.sp,
                        color = ColorTextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Primario
                PrimaryButton(
                    text = "Iniciar Sesión",
                    onClick = { viewModel.login(onLoginSuccess) },
                    loading = state.isSubmitting,
                    enabled = !state.isSubmitting
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Divider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = ColorBorder
                    )
                    Text(
                        text = "O continuar con",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorTextMuted,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = ColorBorder
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones sociales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Google
                    SocialButton(
                        text = "Google",
                        icon = "G",
                        onClick = { /* TODO: integrar con OAuth de Google */ },
                        modifier = Modifier.weight(1f)
                    )
                    // Apple
                    SocialButton(
                        text = "Apple",
                        icon = "\uD83C\uDF4E", // placeholder Apple
                        onClick = { /* TODO: integrar con OAuth de Apple */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿No tienes una cuenta? ",
                fontSize = 13.sp,
                color = ColorTextSecondary
            )
            Text(
                text = "Crear una cuenta",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
                modifier = Modifier.clickable { onRegister() }
            )
        }
    }
}

@Composable
private fun SocialButton(
    text: String,
    icon: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, ColorBorder)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = ColorTextPrimary
        )
    }
}
