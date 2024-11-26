package com.example.laramobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laramobile.activitys.HomeScreen
import com.example.laramobile.activitys.LoginScreen

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginScreen{navController.navigate(Home)}
        }
        composable<Home> {
            HomeScreen()
        }
    }
}