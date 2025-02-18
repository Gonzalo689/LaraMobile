package com.example.laramobile.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laramobile.activitys.HomeScreen
import com.example.laramobile.activitys.LoginScreen
import com.example.laramobile.activitys.SplashScreen
import com.example.laramobile.activitys.TagsScreen
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow

import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.laramobile.ui.theme.Black
import com.example.laramobile.ui.theme.GreenPrm
import com.example.laramobile.ui.theme.Grey
import com.example.laramobile.ui.theme.Red
import com.example.laramobile.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabNavigation() {
    val navController = rememberNavController()
//    var selectedIndex by remember { mutableIntStateOf(0) }
    val screens = listOf(
        Screen.Home, Screen.Tags
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier,
                containerColor = GreenPrm
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                screens.forEach { screen ->

                    NavigationBarItem(
                        label = {
                            Text(text = screen.title!!)
                        },
                        icon = {
                            Icon(imageVector = screen.icon!!, contentDescription = "")
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedTextColor = Color.Gray, selectedTextColor = Color.White
                        ),
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Tags.route) { TagsScreen(navController) }
        }
    }
}





@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
//        composable(Screen.Home.route) { HomeScreen(navController) }
//        composable(Screen.Tags.route) { TagsScreen(navController) }
        // Nav Botton
        composable(Screen.Home.route) { TabNavigation() }
        composable(Screen.Tags.route) { TabNavigation() }

    }
}


