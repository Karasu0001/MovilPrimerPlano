package com.healthsync.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun PlaceholderScreen(
    title: String,
    message: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = message,
            color = ColorTextSecondary,
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Volver")
        }
    }
}
