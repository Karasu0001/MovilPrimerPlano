package com.healthsync.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.navigation.Routes
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextMuted

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, Routes.Dashboard.route),
    BottomNavItem("Dispositivos", Icons.Default.Watch, Routes.QrPairingFlow.route),
    BottomNavItem("Actividad", Icons.Default.MonitorHeart, Routes.Activity.route),
    BottomNavItem("Perfil", Icons.Default.Person, Routes.Profile.route)
)

@Composable
fun BottomNavBar(
    activeRoute: String? = null,
    onItemClick: (String) -> Unit = {}
) {
    BottomAppBar(
        modifier = Modifier.height(64.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                val isActive = activeRoute == item.route
                IconButton(
                    onClick = { onItemClick(item.route) },
                    modifier = Modifier.size(64.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isActive) ColorPrimary else ColorTextMuted,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            color = if (isActive) ColorPrimary else ColorTextMuted
                        )
                    }
                }
            }
        }
    }
}
