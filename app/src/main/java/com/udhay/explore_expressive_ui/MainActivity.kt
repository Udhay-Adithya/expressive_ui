package com.udhay.explore_expressive_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.udhay.explore_expressive_ui.ui.theme.Explore_expressive_uiTheme
import com.udhay.explore_expressive_ui.ui.widgets.ThemeToggleButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme: Boolean by remember { mutableStateOf(false) }
            Explore_expressive_uiTheme(darkTheme = isDarkTheme) {

                ThemeToggleButton(
                    onToggle = { isDarkTheme = !isDarkTheme },
                    isDark = isDarkTheme
                )
            }
        }
    }
}