package com.healthsync.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.dp

val HealthSyncShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),    // badges, pills pequeñas
    small = RoundedCornerShape(12.dp),         // inputs, botones estándar
    medium = RoundedCornerShape(16.dp),        // cards, tarjetas
    large = RoundedCornerShape(20.dp),         // hero de WelcomeScreen
    extraLarge = RoundedCornerShape(40.dp)     // wearable frame
)

object HealthSyncRadii {
    val pill = CircleShape
    val input = RoundedCornerShape(12.dp)
    val button = RoundedCornerShape(12.dp)
    val card = RoundedCornerShape(16.dp)
    val hero = RoundedCornerShape(20.dp)
    val wearable = RoundedCornerShape(40.dp)
}
