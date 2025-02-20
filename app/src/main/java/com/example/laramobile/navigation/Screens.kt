package com.example.laramobile.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String,val title: String? = null, val icon: ImageVector? = null) {
    data object Login : Screen(
        route = "login",
        title = "Login",
        icon = Icons.Default.Home)
    data object Home : Screen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    data object Profile : Screen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Home
    )
    data object SplashScreen : Screen("splash_screen")
    data object Tags : Screen("tags", "Tags", Icons.Default.Home)
}

