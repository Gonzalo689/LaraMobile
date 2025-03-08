package com.example.laramobile.utils

import android.Manifest
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

