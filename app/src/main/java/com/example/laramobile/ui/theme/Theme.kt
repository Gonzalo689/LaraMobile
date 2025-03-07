package com.example.laramobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Red,
    secondary = Red,
    tertiary = Red,
    onPrimary = White,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = GreenPrm,
    secondary = GreenPrm,
    tertiary = GreenPrm,
    background = White,


)
private val DaltonicColorScheme = lightColorScheme(
    primary = Color(0xFF00A8E8),  // Azul accesible
    secondary = Color(0xFFF0A202), // Amarillo
    tertiary = Color(0xFF00A676),  // Verde cian
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)


@Composable
fun LaraMobileTheme(
    themeMode: String,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        "dark" -> DarkColorScheme
        "daltonic" -> DaltonicColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}