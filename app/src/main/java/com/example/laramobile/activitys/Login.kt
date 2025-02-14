package com.example.laramobile.activitys

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laramobile.R
import com.example.laramobile.api.RetrofitInstance
import com.example.laramobile.api.model.LoginRequest
import com.example.laramobile.api.model.LoginResponse
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navigateToHome: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope() // Agregamos el scope

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.lara),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bienvenido a Lara",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navigateToHome()
//                coroutineScope.launch { // Ejecutamos en una corrutina
//                    val response = VerificarUsuario(email, password)
//                    println("gola" +response)
//                    if (response?.success == true) {
//
//                    } else {
//                        showError = response?.message ?: "Error desconocido"
//                    }
//                }
            },
            enabled = checked,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2CA58D),
                disabledContainerColor = Color.Gray
            )
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TermsAndConditions(
            onCheckedChange = { checked = it },
            checked = checked
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = { /* Lógica para recuperar contraseña */ }) {
            Text(
                "¿Olvidaste la contraseña?",
                color = Color(0xFF2CA58D),
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        showError?.let { error ->
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = error, color = Color.Red, fontSize = 14.sp)
        }
    }
}



@Composable
fun TermsAndConditions(onCheckedChange: (Boolean) -> Unit, checked: Boolean) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .padding(10.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF2CA58D),
                uncheckedColor = Color(0xFF2CA58D),
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Acepto los términos y condiciones",
            color = Color(0xFF2CA58D),
            fontWeight = FontWeight.Bold
        )
    }

    // Diálogo con los términos
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("CONSENTIMIENTO DE GRABACIÓN DE DATOS") },
            text = {
                Column(
                    modifier = Modifier
                        .height(300.dp) // Fijamos altura para que sea scrollable
                        .verticalScroll(rememberScrollState()) // Hacemos que el texto sea desplazable
                ) {
                    Text(stringResource(R.string.consentimiento)) }
                   },
            confirmButton = {
                Button(onClick = {
                    onCheckedChange(true)
                    showDialog = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

suspend fun VerificarUsuario(email: String, password: String): LoginResponse? {
    return try {
        RetrofitInstance.apiService.logUser(LoginRequest(email, password))
    } catch (e: Exception) {
        null
    }
}
