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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.healthsync.app.ui.components.AuthWaveBackground
import com.healthsync.app.ui.components.BrandLogo
import com.healthsync.app.ui.components.PrimaryButton
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

    AuthWaveBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            BrandLogo(
                name = "Dialitech",
                tagline = "Fortaleciendo tu camino en la di\u00e1lisis con claridad."
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Cuenta",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Complet\u00e1 tus datos para registrarte.",
                    fontSize = 13.sp,
                    color = ColorTextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                AuthTextField(
                    value = state.fullName,
                    onValueChange = { viewModel.onFullNameChanged(it) },
                    placeholder = "Nombre completo",
                    icon = Icons.Default.Person,
                    error = state.fullNameError,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(12.dp))

                AuthTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    placeholder = "Correo electr\u00f3nico",
                    icon = Icons.Default.Email,
                    error = state.emailError,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(12.dp))

                AuthTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = "Contrase\u00f1a",
                    icon = Icons.Default.Lock,
                    error = state.passwordError,
                    isPassword = true,
                    showPassword = state.showPassword,
                    onTogglePassword = { viewModel.onTogglePasswordVisibility() },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(12.dp))

                AuthTextField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    placeholder = "Confirmar contrase\u00f1a",
                    icon = Icons.Default.Lock,
                    error = state.confirmPasswordError,
                    isPassword = true,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (state.networkError != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ColorErrorBg, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = state.networkError,
                            fontSize = 13.sp,
                            color = ColorErrorRed
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                PrimaryButton(
                    text = "Crear Cuenta",
                    onClick = { viewModel.register(onRegisterSuccess) },
                    loading = state.isSubmitting,
                    enabled = !state.isSubmitting
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\u00bfYa ten\u00e9s una cuenta? ",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = "Iniciar sesi\u00f3n",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.clickable { onLogin() }
                )
            }
        }
    }
}

@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    error: String? = null,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePassword: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = ColorTextMuted) },
            leadingIcon = {
                Icon(icon, contentDescription = null, tint = ColorTextMuted, modifier = Modifier.size(20.dp))
            },
            trailingIcon = if (isPassword && onTogglePassword != null) {
                {
                    IconButton(onClick = { onTogglePassword() }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = ColorTextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
            isError = error != null,
            supportingText = error?.let { { Text(it, color = ColorErrorRed, fontSize = 12.sp) } },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = ColorInputBg,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = ColorPrimary,
                cursorColor = ColorPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
