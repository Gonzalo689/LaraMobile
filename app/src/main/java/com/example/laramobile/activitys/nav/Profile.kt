package com.example.laramobile.activitys.nav

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.laramobile.R
import com.example.laramobile.activitys.AppConfig.user
import com.example.laramobile.navigation.MainNavigation
import com.example.laramobile.ui.theme.LaraMobileTheme
import com.example.laramobile.ui.theme.ThemeViewModel
import com.example.laramobile.ui.theme.White
import com.example.laramobile.utils.Preferences


@Preview
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        // Avatar Image
        Image(
            painter = painterResource(id = R.drawable.default_img), // Reemplaza con tu recurso de imagen
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // User Name with Icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "User Name",
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user!!.nombre ?:"Nombre" ,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold

            )
        }


        Spacer(modifier = Modifier.width(2000.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { /* Lógica para editar perfil */ }) {
                Text(text = "Edit Profile")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { /* Lógica para editar perfil */ }) {
                Text(text = "Edit Profile")
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // User Email with Icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "User Email",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user!!.mail ?:"Email",
                fontSize = 16.sp ,
                color = Color.Gray
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // User Bio with Icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "User Bio",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user!!.rol ?:"Nombre",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        // User Bio with Icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "User Bio",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user!!.fecha_nacimiento ?: "Nombre",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        Spacer(modifier = Modifier.height(8.dp))

        ThemeSelector()

    }
}
@Composable
fun ThemeSelector() {
    var expanded by remember { mutableStateOf(false) }
    val prefer = Preferences(LocalContext.current)
    val themeName = prefer.getTheme()
    val viewodel = ThemeViewModel(prefer)

    Box {
        // Botón que muestra el tema actual
        Button(onClick = { expanded = true }) {
            Text("Modo actual: $themeName")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Lista de opciones de tema
            listOf("dark", "light", "daltonic").forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {

                        viewodel.setTheme(option)
                        expanded = false

                    }
                )
            }
        }

    }

}




