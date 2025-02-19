package com.example.laramobile.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laramobile.activitys.HomeScreen
import com.example.laramobile.activitys.LoginScreen
import com.example.laramobile.activitys.SplashScreen
import com.example.laramobile.activitys.TagsScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.laramobile.ui.theme.GreenPrm

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Lista de pantallas donde NO queremos mostrar la barra de navegación
    val hideNavigationScreens = listOf(Screen.SplashScreen.route, Screen.Login.route)

    Scaffold(
        bottomBar = {
            if (currentRoute !in hideNavigationScreens) {
                BottomNavigationBar(navController,currentRoute)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.SplashScreen.route) { SplashScreen(navController) }
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Tags.route) { TagsScreen(navController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController ,currentRoute: String?) {
    val screens = listOf(Screen.Home, Screen.Tags)

    NavigationBar(containerColor = GreenPrm) {
        screens.forEach { screen ->
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = navBackStackEntry?.destination?.route
            NavigationBarItem(
                label = { Text(text = screen.title!!) },
                icon = { Icon(imageVector = screen.icon!!, contentDescription = null) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}



