package com.example.laramobile.api.model

import com.google.type.DateTime


data class Usuario(
    val fecha_nacimiento: String?,
    val mail: String?,
    val password: String?,
    val rol: String?,
    val nombre: String?,
    val sexo: String?,
    val parent: String?,
    val ultima_conexion: String?,
    val cant_audios: Int?,
    val provincia: String?,
    val enfermedades: List<String>?,
    val dis: List<String>?,
    val font_size: Float?,
    val entidad: String?,
    val observaciones: String?
)