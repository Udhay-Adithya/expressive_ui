package com.udhay.explore_expressive_ui.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeToggleButton(onToggle: () -> Unit, isDark: Boolean){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
//        Button(onClick = onToggle) {
//            Text(if (isDark) "Switch to Light Mode 🌞" else "Switch to Dark Mode")
//
//
//        }
//
//        IconButton(
//            onClick = onToggle
//        ){
//            Icon(
//                imageVector = (if (isDark) Icons.Outlined.Star else Icons.Filled.Star),
//                tint = if (!isDark) Color(0xFFFFD54F) else Color(0xFF3F51B5),
//                contentDescription = if (isDark) "Switch to Light Mode" else "",
//                )
//        }

        Switch(
            checked = isDark,
            onCheckedChange = { onToggle() }
        )
    }
}

@Preview(name = "ThemeToggleButton - Light", showBackground = true)
@Composable
fun PreviewThemeToggleButtonLight() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        ThemeToggleButton(onToggle = {}, isDark = false)
    }
}

@Preview(name = "ThemeToggleButton - Dark", showBackground = true)
@Composable
fun PreviewThemeToggleButtonDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        ThemeToggleButton(onToggle = {}, isDark = true)
    }
}
