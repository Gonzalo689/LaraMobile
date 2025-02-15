package com.example.laramobile.activitys

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import com.example.laramobile.R

import com.example.laramobile.ui.theme.GreenPrm


@Composable
fun HomeScreen(navController: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = {
                // Acción del botón
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Menú",
                modifier = Modifier.size(40.dp),
                tint = GreenPrm

            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lara),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                navController()
                }, enabled = true, modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp), shape = RoundedCornerShape(8.dp),) {
                Text("Ver etiquetas")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Acción del botón
                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text("Grabar audios")
            }
        }
    }

}




// Interesante mas adelante
//@Composable
//fun prueba(){
//    var fraseTag by remember { mutableStateOf<List<String>>(emptyList()) }
//    var users by remember { mutableStateOf<List<Usuario>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    val coroutineScope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            try {
//                val posts = RetrofitInstance.apiService.getUsers()
//                fraseTag = posts.map { it.mail ?: "unknown" }
//                users = posts
//                isLoading = false
//            } catch (e: Exception) {
//                errorMessage = "Error: ${e.message}"
//                isLoading = false
//            }
//        }
//    }
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        content = { innerPadding ->
//            Column(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
//            ) {
//                when {
//                    isLoading -> LoadingIndicator()
//                    errorMessage != null -> ErrorMessage(errorMessage ?: "Unknown error")
//                    else -> {
//                        // Mostrar la lista de usuarios
//                        LazyColumn(
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            items(fraseTag.zip(users)) { (tag, user) ->
//                                PostItem(tag = tag, user = user)
//                            }
//                        }
//
//
//                    }
//                }
//            }
//        }
//    )
//
//
//}
//
//@Composable
//fun LoadingIndicator() {
//    Text(text = "Loading...", modifier = Modifier.padding(16.dp))
//}
//
//@Composable
//fun ErrorMessage(message: String) {
//    Text(text = message, modifier = Modifier.padding(16.dp), color = Color.Red)
//}
//
//
//@Composable
//fun PostItem(tag: String, user: Usuario) {
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Tag: $tag",
//            style = TextStyle(fontSize = 18.sp)
//        )
//        Text(
//            text = "Creator: ${user.nombre}, Email: ${user.mail}",
//            style = TextStyle(fontSize = 16.sp)
//        )
//    }
//}