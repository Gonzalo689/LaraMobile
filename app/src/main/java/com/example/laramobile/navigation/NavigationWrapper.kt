package com.example.laramobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laramobile.activitys.HomeScreen
import com.example.laramobile.activitys.LoginScreen
import com.example.laramobile.activitys.SplashScreen
import com.example.laramobile.activitys.TagsScreen


@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SplashScreen) {
        composable<Login> {
            LoginScreen{navController.navigate(Home)}
        }
        composable<Home> {
            HomeScreen{navController.navigate(Tags)}
        }
        composable<SplashScreen> {
            SplashScreen(navController)
        }
        composable<Tags> {
            TagsScreen()
        }
    }
}