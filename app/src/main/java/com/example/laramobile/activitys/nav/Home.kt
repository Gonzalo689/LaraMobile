package com.example.laramobile.activitys.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laramobile.R
import com.example.laramobile.activitys.AppConfig
import com.example.laramobile.api.getPhrasesImpl
import com.example.laramobile.navigation.navigateToTags
import com.example.laramobile.ui.theme.*


@Composable
fun HomeScreen(navController: NavController) {
//    pruebaUser()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

    ) {
        Image(
            painter = painterResource(id = R.drawable.lara),
            contentDescription = "Logo",
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Tus últimos audios",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))


        GetPhrases(navController)
        Spacer(modifier = Modifier.height(24.dp))


    }
}
@Composable
fun GetPhrases(navController : NavController) {
    var tagList by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
//    val name by remember { mutableStateOf("Mario") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        AppConfig.user?.nombre?.let {
            getPhrasesImpl(coroutineScope, it, { tags ->
                tagList = tags
                isLoading = false
            }, { error ->
                errorMessage = error
                isLoading = false
            })
        }
        if (tagList.isEmpty()) {
            errorMessage = "No se encontraron frases"
            isLoading = false
        }
    }
    when {
        isLoading -> {
            CircularProgressIndicator()
        }
        errorMessage != null -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = errorMessage!!, color = Gray, fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = { navigateToTags(navController) }) {
                    Text("Grabar audios")
                }
            }
        }
        else -> {
            Column {
                tagList.chunked(1).forEach { rowItems ->
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
                        repeat(1 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
