package com.example.laramobile.activitys

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laramobile.api.model.User
import com.example.laramobile.navigation.MainNavigation

import com.example.laramobile.ui.theme.LaraMobileTheme
import com.example.laramobile.utils.Preferences

object AppConfig {
    var user: User? = null
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val theme = Preferences(this).getTheme()


        setContent {
//            var themeMode by remember { mutableStateOf("light") }
            LaraMobileTheme(theme) {

                MainNavigation()
            }
        }
    }
}

fun pruebaUser(){
    AppConfig.user = User(
        fecha_nacimiento = "1990-05-15",
        mail = "usuario@ejemplo.com",
        rol = "Cliente",
        nombre = "Mario",
        sexo = "Masculino",
        parent = null,
        ultima_conexion = "2025-02-20 14:30:00",
        cant_audios = 10,
        provincia = "Madrid",
        enfermedades = listOf("Hipertensión", "Diabetes"),
        dis = listOf("Ninguna"),
        font_size = 14,
        entidad = "Empresa XYZ",
        observaciones = "Usuario activo con permisos de administrador."
    )
}



