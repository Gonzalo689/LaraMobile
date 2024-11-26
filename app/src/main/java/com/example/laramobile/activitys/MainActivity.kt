package com.example.laramobile.activitys

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                AppNavigation()
            }
        }
    }
}

@Composable
fun PostList(navController: NavController){
    var fraseTag by remember { mutableStateOf<List<String>>(emptyList()) }
    var users by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                when {
                    isLoading -> LoadingIndicator()
                    errorMessage != null -> ErrorMessage(errorMessage ?: "Unknown error")
                    else -> {
                        // Mostrar la lista de usuarios
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(fraseTag.zip(users)) { (tag, user) ->
                                PostItem(tag = tag, user = user, onClick = {
                                    navController.navigate("loginScreen")
                                })
                            }
                        }
                        // Botón al final
                        NavigateToLoginButton()
                    }
                }
            }
        }
    )


}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "postList") {
        composable("postList") { PostList(navController) }
        composable("loginScreen") { Login() }
    }
}
@Composable
fun NavigateToLoginButton() {
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(context, Login::class.java) // Especifica la actividad de destino
        context.startActivity(intent)
    }) {
        Text("Ir a Login")
    }
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
fun PostItem(tag: String, user: Usuario, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }
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


