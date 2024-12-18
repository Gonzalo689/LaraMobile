package com.example.laramobile.activitys

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laramobile.api.RetrofitInstance
import com.example.laramobile.api.model.Usuario

@Composable
fun HomeScreen(){
    var fraseTag by remember { mutableStateOf<List<String>>(emptyList()) }
    var users by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val posts = RetrofitInstance.apiService.getFrases()
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
                when {
                    isLoading -> LoadingIndicator()
                    errorMessage != null -> ErrorMessage(errorMessage ?: "Unknown error")
                    else -> {
                        // Mostrar la lista de usuarios
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(fraseTag.zip(users)) { (tag, user) ->
                                PostItem(tag = tag, user = user)
                            }
                        }


                    }
                }
            }
        }
    )


}


@Composable
fun LoadingIndicator() {
    Text(text = "Loading...", modifier = Modifier.padding(16.dp))
}

@Composable
fun ErrorMessage(message: String) {
    Text(text = message, modifier = Modifier.padding(16.dp), color = Color.Red)
}


@Composable
fun PostItem(tag: String, user: Usuario) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
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