package com.example.laramobile.ui.theme


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.laramobile.utils.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(private val preferences: Preferences) : ViewModel() {

    private val _themeMode = MutableStateFlow(preferences.getTheme())

    fun setTheme(mode: String) {
        viewModelScope.launch {
            preferences.saveTheme(mode)
            _themeMode.value = mode

        }
    }
}

