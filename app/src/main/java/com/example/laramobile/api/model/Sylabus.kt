package com.example.laramobile.api.model



data class Sylabus  (
    val texto:String?,
    val creador: Usuario?,
    val tags: List<String>?,
    val audios: List<Audios>?,
    val fecha_creacion: String?
)