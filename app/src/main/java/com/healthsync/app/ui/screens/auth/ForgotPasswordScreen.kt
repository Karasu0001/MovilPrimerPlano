package com.healthsync.app.ui.screens.auth

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.healthsync.app.ui.theme.ColorSuccessBg
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit = {},
    viewModel: ForgotPasswordViewModel = viewModel()
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
            text = "Recuperar Contraseña",
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

        if (state.isSubmitted) {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .background(ColorSuccessBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\u2713",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorSuccessGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Correo Enviado",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTextPrimary
                    )
                    Text(
                        text = "Hemos enviado un enlace de recuperación a ${state.email}. Revisa tu bandeja de entrada.",
                        fontSize = 14.sp,
                        color = ColorTextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PrimaryButton(
                        text = "Volver al inicio de sesión",
                        onClick = onBack
                    )
                }
            }
        } else {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTextPrimary
                    )
                    Text(
                        text = "Ingresa tu correo electrónico y te enviaremos un enlace para restablecerla.",
                        fontSize = 14.sp,
                        color = ColorTextSecondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

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

                    PrimaryButton(
                        text = "Enviar enlace de recuperación",
                        onClick = { viewModel.onSubmit() },
                        loading = state.isSubmitting,
                        enabled = !state.isSubmitting
                    )
                }
            }
        }
    }
}
