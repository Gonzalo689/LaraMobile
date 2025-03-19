package com.example.laramobile.activitys.nav.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laramobile.activitys.obtenerTagsUnicos
import com.example.laramobile.activitys.obtenerTextosUnicos
import com.example.laramobile.activitys.pruebasylabus
import com.example.laramobile.api.getTagsImpl
import com.example.laramobile.navigation.Screen
import com.example.laramobile.ui.theme.Pink80


// no funciona con navController el preview
//@Preview(showBackground = true)
@Composable
fun TagsScreen(navController: NavController){
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de búsqueda
        Text(text = "Etiquetas", fontSize = 22.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Espaciado alrededor del Row
            verticalAlignment = Alignment.CenterVertically // Alineación vertical
        ) {
            // Campo de texto
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text("Buscar", style = MaterialTheme.typography.body2.copy(color = Color.Gray))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray
                    )
                },
                modifier = Modifier
                    .weight(1f) // Hace que el TextField ocupe el espacio disponible
                    .padding(end = 8.dp) // Espaciado entre el TextField y el botón
                    .clip(RoundedCornerShape(50)) // Forma redondeada
                    .background(Color(0xFFF1F1F1)) // Fondo suave
                    .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(50)), // Borde suave
                singleLine = true
            )

            // Espaciador entre el TextField y el botón
            Spacer(modifier = Modifier.width(8.dp))

            // Botón de búsqueda
            Button(
                onClick = { /* Acción de búsqueda */ },
                shape = RoundedCornerShape(50), // Hacer el botón redondeado
            ) {
                Text("Buscar", color = Color.White) // Texto dentro del botón
            }
        }


        Spacer(modifier = Modifier.height(50.dp))

        // Etiquetas recientes
        Text(text = "Menos usadas", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))

        // cargar Tags TODO CORRECTO EL PRIMERO
//        GetSylabus(navController)
        GetSylabus2(navController)

        Spacer(modifier = Modifier.height(50.dp))

        // Botón de búsqueda aleatoria
        Text(text = "Aleatoria", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* Acción de búsqueda aleatoria */ },
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Text("Buscar", color = Color.White)
        }
    }
}

@Composable
fun GetSylabus(navController: NavController) {
    var tagList by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        getTagsImpl(coroutineScope, { tags ->
            tagList = tags
            isLoading = false
        }, { error ->
            errorMessage = error
            isLoading = false

        })
    }
    when {
        isLoading -> {
            CircularProgressIndicator()

        }
        errorMessage != null -> {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(text = errorMessage!!, color = Pink80, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

        }
        else -> {
            Column {
                tagList.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowItems.forEach { tag ->
                            OutlinedButton(
                                onClick = { navController.navigate("${Screen.AudiosRecordingTag}/$tag") },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(tag)
                            }
                        }
                        repeat(2 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun GetSylabus2(navController: NavController) {
    var tagList by remember { mutableStateOf<List<String>>(emptyList()) }

    Column {
        tagList = obtenerTagsUnicos() // TODO QUITAR ESTA PARTE

        tagList.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { tag ->
                    OutlinedButton(
                        onClick = { navController.navigate("${Screen.AudiosRecordingTag}/$tag") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(tag)
                    }
                }
                repeat(2 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}





