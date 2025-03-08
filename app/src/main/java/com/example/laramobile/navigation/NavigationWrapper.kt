package com.example.laramobile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laramobile.activitys.nav.HomeScreen
import com.example.laramobile.activitys.LoginScreen
import com.example.laramobile.activitys.SplashScreen
import com.example.laramobile.activitys.nav.tag.TagsScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.laramobile.activitys.nav.AudioRecordingScreen
import com.example.laramobile.activitys.nav.ProfileScreen
import com.example.laramobile.activitys.nav.tag.AudiosRecordingTagScreen
import com.example.laramobile.ui.theme.*

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideNavigationScreens = listOf(Screen.SplashScreen.route, Screen.Login.route)

    Scaffold(
        bottomBar = {
            val recordTag = currentRoute?.startsWith(Screen.AudiosRecordingTag.route)
            if (currentRoute !in hideNavigationScreens && recordTag != true) {
                BottomNavigationBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.RecordAudio.route
//                startDestination = Screen.AudiosRecordingTag.route+"/Prueba1"

            ) {
                composable(Screen.SplashScreen.route) { SplashScreen(navController) }
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.Home.route) { HomeScreen(navController) }
                composable(Screen.Tags.route) { TagsScreen(navController) }
                composable(Screen.Profile.route) { ProfileScreen() }
                composable(Screen.RecordAudio.route) {AudioRecordingScreen() }
                composable(
                    Screen.AudiosRecordingTag.route + "/{tag}",
                    arguments = listOf(navArgument("tag") { type = NavType.StringType })
                ) { backStackEntry ->
                    val tag = backStackEntry.arguments?.getString("tag")?:""
                    AudiosRecordingTagScreen(tag = tag)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    val screens = listOf(Screen.Home,Screen.RecordAudio, Screen.Tags, Screen.Profile)
    NavigationBar(
        containerColor = GreenPrm,
//        modifier = Modifier.height(56.dp)
        ) {
        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(
                        imageVector = screen.icon!!,
                        contentDescription = null
                    )
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
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = PurpleGrey40,
                    unselectedIconColor = White,
                    selectedTextColor = PurpleGrey40,
                    unselectedTextColor = White,
                    indicatorColor = GreenPrm
                )
            )
        }
    }
}

fun navigateToTags(navController: NavController) {
    navController.navigate(Screen.Tags.route) {
        // Asegúrate de no apilar pantallas innecesarias
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}




