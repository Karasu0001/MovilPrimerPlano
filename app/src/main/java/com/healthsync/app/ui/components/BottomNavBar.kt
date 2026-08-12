package com.healthsync.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.navigation.Routes
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextMuted
import kotlin.math.roundToInt

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, Routes.Dashboard.route),
    BottomNavItem("Alertas", Icons.Default.Warning, Routes.Alerts.route),
    BottomNavItem("Perfil", Icons.Default.Person, Routes.Profile.route)
)

@Composable
fun BottomNavBar(
    activeRoute: String? = null,
    onItemClick: (String) -> Unit = {}
) {
    val activeIndex = bottomNavItems.indexOfFirst { it.route == activeRoute }.coerceAtLeast(0)

    val ballX = remember { Animatable(0f) }
    var barWidthPx by remember { mutableStateOf(0f) }
    val itemWidth = barWidthPx / bottomNavItems.size

    LaunchedEffect(activeIndex, itemWidth) {
        if (itemWidth > 0f) {
            ballX.animateTo(
                targetValue = itemWidth * activeIndex + itemWidth / 2f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .onGloballyPositioned { barWidthPx = it.size.width.toFloat() }
    ) {
        Canvas(
            modifier = Modifier.fillMaxWidth().height(72.dp)
        ) {
            val w = size.width
            val h = size.height

            val bgPath = Path().apply {
                moveTo(0f, h * 0.25f)
                cubicTo(w * 0.35f, h * 0.0f, w * 0.65f, h * 0.0f, w, h * 0.25f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(bgPath, Color.White, style = Fill)

            drawLine(
                color = Color(0xFFE5E7EB),
                start = Offset(0f, h * 0.25f),
                end = Offset(w, h * 0.25f),
                strokeWidth = 1.dp.toPx()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .offset { IntOffset(0, 0) },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEachIndexed { index, item ->
                val isActive = activeIndex == index

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(64.dp)
                ) {
                    if (isActive) {
                        val offsetXPx = ballX.value - (itemWidth * index + itemWidth / 2f)
                        Box(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        x = offsetXPx.roundToInt(),
                                        y = 0
                                    )
                                }
                                .size(48.dp)
                        ) {
                            Canvas(modifier = Modifier.size(48.dp)) {
                                drawCircle(
                                    color = ColorPrimary,
                                    radius = size.minDimension / 2f
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = { onItemClick(item.route) },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isActive) Color.White else ColorTextMuted,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
