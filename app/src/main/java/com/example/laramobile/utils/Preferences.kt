package com.example.laramobile.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Preferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    // Guardar el tema seleccionado
    fun saveTheme(mode: String) {
        sharedPreferences.edit() { putString("theme_mode", mode) }
    }

    // Obtener el tema guardado, por defecto será "default"
    fun getTheme(): String {
        return sharedPreferences.getString("theme_mode", "light") ?: "light"
    }

}