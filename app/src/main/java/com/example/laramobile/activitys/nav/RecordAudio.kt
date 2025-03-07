package com.example.laramobile.activitys.nav
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException

import android.app.Activity
import android.media.MediaPlayer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Preview
@Composable
fun AudioRecordingScreen() {
    var recordText by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var mediaPlayer: MediaPlayer? = remember { null }
    var isPlaying by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Usar una ubicación fija para el archivo de audio
    val audioFilePath = remember {
        context.getExternalFilesDir(null)?.absolutePath + "/mi_grabacion.3gp"
    }
    // MediaRecorder instance
    val recorder = remember { MediaRecorderWrapper(context) }

    // Estado para saber si hay una grabación guardada
    var hasRecording by remember { mutableStateOf(File(audioFilePath).exists()) }

    // Comprobar permisos de grabación al iniciar la pantalla
    var permissionGranted by remember { mutableStateOf(false) }

    val permissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            permissionGranted = granted
        }
    )

    LaunchedEffect(Unit) {
        // Verifica si el permiso ya está concedido
        permissionGranted = hasRecordPermission(context)

        if (!permissionGranted) {
            // Solicitar el permiso en tiempo de ejecución
            permissionRequestLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            if (permissionGranted) {
                try {
                    recorder.start(audioFilePath)
                } catch (e: IOException) {
                    isRecording = false
                }
            } else {
                isRecording = false
                // En una app real, aquí solicitarías los permisos
            }
        } else if (recorder.isRecording) {
            recorder.stop()
            // Actualizar el estado de grabación
            hasRecording = File(audioFilePath).exists()
        }
    }

    // Clean up when component is disposed
    DisposableEffect(Unit) {
        val file = File(audioFilePath)

        // Observa el ciclo de vida de la actividad
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                // Cuando la actividad se destruye, elimina el archivo
                if (file.exists()) {
                    file.delete()
                }
            }
        }

        // Registrar el observer para detectar cuando la actividad se destruye
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            recorder.release()
            mediaPlayer?.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
            if (file.exists()) {
                file.delete()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Header with title
        Text(
            text = "Grabar mi audio",
            color = Color(0xFF4CAF8D),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        )

        // Text input field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            if (recordText.isEmpty()) {
                Text(
                    text = "Escribir la frase aquí",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }

            BasicTextField(
                value = recordText,
                onValueChange = { recordText = it },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }

        // Show recording status or file info
        if (isRecording) {
            Text(
                text = "Grabando...",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        } else if (hasRecording) {
            Text(
                text = "Audio grabado",
                color = Color(0xFF4CAF8D),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Reproductor de audio
            Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        if (isPlaying) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                        } else {
                            mediaPlayer = MediaPlayer().apply {
                                setDataSource(audioFilePath)
                                prepare()
                                start()
                                setOnCompletionListener {
                                    isPlaying = false
                                }
                            }
                        }
                        isPlaying = !isPlaying
                    }
                ) {
                    Text(text = if (isPlaying) "Detener Reproducción" else "Reproducir Audio")
                }
            }
        }

        // Spacer
        Spacer(modifier = Modifier.height(80.dp))

        // Microphone button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                if (isRecording) {
                                    isRecording = false
                                } else {
                                    isRecording = true
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isRecording) Icons.Rounded.Clear else Icons.Rounded.Phone,
                    contentDescription = if (isRecording) "Detener Grabación" else "Iniciar Grabación",
                    modifier = Modifier.size(32.dp),
                    tint = if (isRecording) Color.Red else Color.Gray
                )
            }
        }

        // Add extra space at the bottom
        Spacer(modifier = Modifier.height(40.dp))
    }
}

// Helper class to handle MediaRecorder lifecycle
class MediaRecorderWrapper(private val context: Context) {
    private var recorder: MediaRecorder? = null
    var isRecording = false
        private set

    fun start(filePath: String) {
        // Eliminar el archivo anterior si existe
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }

        // Crear el MediaRecorder según la versión de Android
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }

        recorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(filePath)
            prepare()
            start()
            isRecording = true
        }
    }

    fun stop() {
        if (isRecording) {
            try {
                recorder?.stop()
            } catch (e: RuntimeException) {
                // Ignorar. Esto ocurre cuando stop se llama inmediatamente después de start
            }
            recorder?.reset()
            isRecording = false
        }
    }

    fun release() {
        stop()
        recorder?.release()
        recorder = null
    }
}

fun hasRecordPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED
}




