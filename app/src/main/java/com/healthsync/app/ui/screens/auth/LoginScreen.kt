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
import androidx.compose.ui.text.style.TextAlign
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

    AuthWaveBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            BrandLogo(
                name = "Dialitech",
                tagline = "Fortaleciendo tu camino en la di\u00e1lisis con claridad."
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Iniciar Sesi\u00f3n",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ingresa tus datos para continuar.",
                    fontSize = 13.sp,
                    color = ColorTextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    placeholder = { Text("Correo electr\u00f3nico", color = ColorTextMuted) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = ColorTextMuted, modifier = Modifier.size(20.dp))
                    },
                    isError = state.emailError != null,
                    supportingText = state.emailError?.let { { Text(it, color = ColorErrorRed, fontSize = 12.sp) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
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

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = { Text("Contrase\u00f1a", color = ColorTextMuted) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = ColorTextMuted, modifier = Modifier.size(20.dp))
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                            Icon(
                                imageVector = if (state.showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = ColorTextMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = state.passwordError != null,
                    supportingText = state.passwordError?.let { { Text(it, color = ColorErrorRed, fontSize = 12.sp) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
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

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Recordarme",
                            fontSize = 12.sp,
                            color = ColorTextSecondary
                        )
                    }
                    Text(
                        text = "\u00bfOlvidaste tu contrase\u00f1a?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorPrimary,
                        modifier = Modifier.clickable { onForgotPassword() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                    text = "Iniciar Sesi\u00f3n",
                    onClick = { viewModel.login(onLoginSuccess) },
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
                    text = "\u00bfNo ten\u00e9s una cuenta? ",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = "Crear cuenta",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.clickable { onRegister() }
                )
            }
        }
    }
}
