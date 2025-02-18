package com.example.laramobile.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.laramobile.navigation.NavigationWrapper
import com.example.laramobile.navigation.TabNavigation
import com.example.laramobile.ui.theme.LaraMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaraMobileTheme {
                NavigationWrapper()
            }
        }
    }
}




