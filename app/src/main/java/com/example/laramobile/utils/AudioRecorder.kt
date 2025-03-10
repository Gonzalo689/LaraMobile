package com.example.laramobile.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import java.io.File
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

//Formatos a los que se puede pasar el audio
/*
3GPP	.3gp	AMR-NB, AMR-WB	Android 1.0+
MPEG_4	.mp4, .m4a	AAC	Android 3.0+
AMR_NB	.amr	AMR-NB (baja calidad)	Android 1.0+
AMR_WB	.amr	AMR-WB (mejor calidad)	Android 1.0+
WEBM	.webm	Vorbis, Opus	Android 10+
OGG (Opus)	.ogg	Opus	No soportado en MediaRecorder

El formato mas simple para pasar luego a .wav es 3GP por si hay que transformarlo en un futuro
 */

// Helper class to handle MediaRecorder lifecycle
class MediaRecorderWrapper(private val context: Context) {
    private var recorder: MediaRecorder? = null
    val audioFilePath = context.getExternalFilesDir(null)?.absolutePath + "/mi_grabacion.3gp"
//    var permissionGranted = hasRecordPermission(context)
    var isRecording = false
        private set

    fun start(filePath: String) {

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

fun hasAudioPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED
}


@Composable
fun CleanUp(recorder: MediaRecorderWrapper, mediaPlayer: MediaPlayer?, audioFilePath: String) {
    val lifecycleOwner = LocalLifecycleOwner.current

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
}
@Composable
fun RequestAudioPermission(
    context: Context,
    onPermissionGranted: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val activity = context as? Activity
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted() // El permiso fue concedido
        } else {
            // El permiso fue denegado
            if (activity != null && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
                // El usuario marcó "No volver a preguntar"
                showDialog.value = true
            }
        }
    }

    // Solicitar permisos al iniciar
    LaunchedEffect(Unit) {
        if (!hasAudioPermission(context)) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        } else {
            onPermissionGranted()
        }
    }

    if (showDialog.value) {
        ShowSettingsDialog {
            showDialog.value = false
        }
    }
}

@Composable
fun ShowSettingsDialog(onDismiss: () -> Unit) {
    // Obtener el contexto actual
    val context = LocalContext.current
    val activity = context as? Activity  // Convertir el contexto a Activity

    // Asegurarse de que el contexto sea de tipo Activity
    activity?.let {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Permiso necesario") },
            text = { Text(text = "Para grabar audio, debes habilitar el permiso en Configuración.") },
            confirmButton = {
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = "package:${it.packageName}".toUri()
                    it.startActivity(intent)
                }) {
                    Text("Ir a ajustes")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}

