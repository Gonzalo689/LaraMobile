package com.example.laramobile.api.model



data class User(
    val fecha_nacimiento: String?,
    val mail: String,
    val rol: String?,
    val nombre: String?,
    val sexo: String?,
    val parent: String?,
    val ultima_conexion: String?,
    val cant_audios: Int?,
    val provincia: String?,
    val enfermedades: List<String>?,
    val dis: List<String>?,
    val font_size: Int?,
    val entidad: String?,
    val observaciones: String?
)