package com.healthsync.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorPrimaryHover
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorBorder

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    variant: ButtonVariant = ButtonVariant.Primary
) {
    val colors = when (variant) {
        ButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = ColorPrimary,
            contentColor = Color.White,
            disabledContainerColor = ColorPrimary.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        )
        ButtonVariant.Outline -> ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = ColorTextPrimary,
            disabledContainerColor = Color.White.copy(alpha = 0.5f),
            disabledContentColor = ColorTextPrimary.copy(alpha = 0.5f)
        )
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled && !loading,
        shape = RoundedCornerShape(12.dp),
        colors = colors,
        border = if (variant == ButtonVariant.Outline) {
            androidx.compose.foundation.BorderStroke(1.dp, ColorBorder)
        } else null
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

enum class ButtonVariant { Primary, Outline }
