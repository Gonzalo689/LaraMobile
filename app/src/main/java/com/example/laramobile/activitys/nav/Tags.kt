package com.example.laramobile.activitys.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laramobile.api.getTagsImpl
import com.example.laramobile.ui.theme.GreenPrm
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
        Row {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_search),
                        contentDescription = "Buscar..."
                    )
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* Acción de búsqueda */ }
            ) {
                Text("Buscar", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Etiquetas recientes
        Text(text = "Menos usadas", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))

        // cargar Tags
        GetSylabus()

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
fun GetSylabus() {
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
                                onClick = {  },
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


