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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laramobile.R


@Composable
fun LoginScreen(navigateToHome: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

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
            onClick = { navigateToHome() },
            enabled = checked, // Solo habilitado si aceptó los términos
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
                    Text("Nombre de la aplicación: PIA Lara\n" +
                            "\n" +
                            "Nombre del responsable del tratamiento de datos: Conselleria de Educación, Cultura y Deporte dependiente de la Generalitat Valenciana\n" +
                            "\n" +
                            "Finalidad del tratamiento de datos: La aplicación PIA Lara es un proyecto para la creación de una aplicación que usará archivos de audio de forma que se facilite su entendimiento mediante el uso de inteligencia artificial. Para ello se utilizarán los audios de los usuarios/interesados en los entrenamientos, pero nunca para ser mostrados a ningún usuario ni cedidos a terceros\n" +
                            "\n" +
                            "Base legitimadora del tratamiento de datos: El tratamiento de datos se realiza en base al consentimiento del usuario, que se manifiesta al aceptar los términos y condiciones de la aplicación y la firma y aceptación del presente documento.\n" +
                            "\n" +
                            "Información necesaria para el interesado: Para utilizar la aplicación, el usuario debe proporcionar su nombre y apellidos. Además, se recogerá la información generada por el usuario en el uso de la aplicación, como las grabaciones de los audios y textos creados por los/as mismos/as.\n" +
                            "\n" +
                            "Derechos del interesado en relación con sus datos personales: El usuario podrá revocar su consentimiento en cualquier momento. Además, el usuario tiene derecho a acceder, rectificar y suprimir sus datos personales, así como a limitar y oponerse al tratamiento de los mismos. También tiene derecho a la portabilidad de sus datos y a presentar una reclamación ante la autoridad de control competente.\n" +
                            "\n" +
                            "Forma de ejercicio de los derechos del interesado: Para ejercitar sus derechos, el usuario debe enviar un correo electrónico a protecciondedatos@piafplara.es, indicando su nombre y apellidos, dirección de correo electrónico y el derecho que desea ejercitar.\n" +
                            "\n" +
                            "Si continúa usando la aplicación, se entiende que usted ha leído, comprende y acepta los términos anteriormente expresados.") }
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


fun verificarUsuario(email: String, password: String){

}