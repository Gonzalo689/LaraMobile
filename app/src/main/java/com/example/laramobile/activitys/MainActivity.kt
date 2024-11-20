package com.example.laramobile.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laramobile.api.RetrofitInstance
import com.example.laramobile.api.model.Usuario
import com.example.laramobile.ui.theme.LaraMobileTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaraMobileTheme {
                PostListScreen()
            }
        }
    }
}

@Composable
fun PostListScreen() {
    var fraseTag by remember { mutableStateOf<List<String>>(emptyList()) }
    var users by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Llamada al API usando Retrofit
    LaunchedEffect(Unit) {
        try {
            val posts = RetrofitInstance.apiService.getFrases()
            // Asumiendo que cada post tiene un creador que es un objeto User
            fraseTag = posts.map { it.tag }
            users = posts.map { it.creador }
            isLoading = false
        } catch (e: Exception) {
            errorMessage = "Error: ${e.message}"
            isLoading = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                if (isLoading) {
                    Text(text = "Loading...", modifier = Modifier.padding(16.dp))
                } else if (errorMessage != null) {
                    Text(text = errorMessage ?: "Unknown error", modifier = Modifier.padding(16.dp))
                } else {
                    // Mostrar la lista de tags y correos de los usuarios
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(fraseTag.zip(users)) { (tag, user) ->
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Tag: $tag",
                                    style = TextStyle(fontSize = 18.sp)
                                )
                                Text(
                                    text = "Creator: ${user.nombre}, Email: ${user.mail}",
                                    style = TextStyle(fontSize = 16.sp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

