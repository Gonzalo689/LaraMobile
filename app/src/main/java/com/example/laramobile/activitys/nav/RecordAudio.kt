package com.example.laramobile.activitys.nav
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.border
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
import java.io.File
import java.io.IOException
import com.example.laramobile.R
import android.media.MediaPlayer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.laramobile.utils.CleanUp
import com.example.laramobile.utils.MediaRecorderWrapper
import com.example.laramobile.utils.hasAudioPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.laramobile.ui.theme.GreenPrm
import com.example.laramobile.utils.RequestAudioPermission
import kotlinx.coroutines.delay

@Preview
@Composable
fun AudioRecordingScreen() {
    var recordText by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var mediaPlayer: MediaPlayer? = remember { null }
    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // MediaRecorder instance
    val recorder = remember { MediaRecorderWrapper(context) }

    val audioFilePath = recorder.audioFilePath

    // Estado para saber si hay una grabación guardada
    var hasRecording by remember { mutableStateOf(File(audioFilePath).exists()) }

    // Comprobar permisos de grabación al iniciar la pantalla
    var permissionGranted by remember { mutableStateOf(hasAudioPermission(context)) }

    //recordar el resultado
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            permissionGranted = granted
        }
    )
    RequestAudioPermission(context = context)

    if (isRecording) {
        if (permissionGranted) {
            try {
                recorder.start(audioFilePath)
            } catch (e: IOException) {
                isRecording = false
            }
        } else {
            isRecording = false  // Detener la grabación para evitar errores

            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)

        }
    } else if (recorder.isRecording) {
        recorder.stop()
        // Actualizar el estado de grabación
        hasRecording = File(audioFilePath).exists()
    }

    CleanUp(recorder, mediaPlayer, audioFilePath)

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
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(bottom = 26.dp, top = 12.dp)
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
        AnimatedVisibility(isRecording) {
            Text(
                text = "Grabando...",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
        }
        AnimatedVisibility(!isRecording && hasRecording) {
            AudioMessageBubble(audioFilePath = audioFilePath)
        }

//        AnimatedVisibility (!isRecording && hasRecording) {
//            Text(
//                text = "Audio grabado",
//                color = Color(0xFF4CAF8D),
//                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
//                textAlign = TextAlign.Center
//            )
//
//            // Reproductor de audio
//            Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), contentAlignment = Alignment.Center) {
//                Button(
//                    onClick = {
//                        if (isPlaying) {
//                            mediaPlayer?.stop()
//                            mediaPlayer?.release()
//                            mediaPlayer = null
//                        } else {
//                            mediaPlayer = MediaPlayer().apply {
//                                setDataSource(audioFilePath)
//                                prepare()
//                                start()
//                                setOnCompletionListener {
//                                    isPlaying = false
//                                }
//                            }
//                        }
//                        isPlaying = !isPlaying
//                    }
//                ) {
//                    Text(text = if (isPlaying) "Detener Reproducción" else "Reproducir Audio")
//                }
//            }
//        }

        // Spacer
        Spacer(modifier = Modifier.height(80.dp))

        // Microphone button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.Center,

        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isRecording = !isRecording
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {

                DisplayGif(isRecording)
            }
        }

        // Add extra space at the bottom
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun DisplayGif(isRecording: Boolean) {
    // Cargar la animación Lottie
    val composition by rememberLottieComposition(
        spec =  LottieCompositionSpec.RawRes(R.raw.animation_record)
         // Si usas PNG, simplemente cargarlo como un recurso
    )

    // Mostrar la animación Lottie o la imagen PNG
    Box(modifier = Modifier.fillMaxSize()) {
        if (isRecording) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever, // Repite la animación infinitamente
                modifier = Modifier.align(Alignment.Center).size(60.dp) // Centra la animación
            )
        } else {
            // Si no está grabando, mostrar un PNG estático
            Image(
                painter = painterResource(id = R.drawable.microphone_icon), // Ruta a tu imagen PNG
                contentDescription = "Microphone Icon",
                modifier = Modifier.align(Alignment.Center).size(48.dp) // Centra la imagen
            )
        }
    }
}
@Composable
fun AudioMessageBubble(
    audioFilePath: String,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var duration by remember { mutableIntStateOf(0) }

    // Obtener duración del audio
    LaunchedEffect(audioFilePath) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioFilePath)
            prepare()
            duration = this.duration / 1000
        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GreenPrm),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón Play/Pausa
            IconButton(
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
                            setOnCompletionListener { isPlaying = false }
                        }
                    }
                    isPlaying = !isPlaying
                }
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Rounded.Clear else Icons.Rounded.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White
                )
            }

            // Onda de audio
            AudioWaveform(isPlaying)
            Spacer(modifier = Modifier.weight(1f))

            // Duración del audio
            Text(
                text = "${duration}s",
                color = Color.White,
                fontSize = 14.sp
            )

        }
    }
}

@Composable
fun AudioWaveform(isPlaying: Boolean) {
    val barCount = 32
    val barHeights = remember { List(barCount) { mutableFloatStateOf((5..20).random().toFloat()) } }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            barHeights.forEachIndexed { index, height ->
                height.floatValue = (5..20).random().toFloat()
            }
            delay(200) // Velocidad de cambio de las barras
        }
    }

    Row(
        modifier = Modifier
            .height(24.dp)
            .width(150.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        barHeights.forEach { height ->
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(height.floatValue.dp)
                    .background(Color.White, shape = RoundedCornerShape(2.dp))
                    .padding(horizontal = 2.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}








