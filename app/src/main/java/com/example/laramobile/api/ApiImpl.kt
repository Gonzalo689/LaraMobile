package com.example.laramobile.api

import android.util.Log
import com.example.laramobile.api.model.LoginResponse
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.launch
import retrofit2.HttpException

fun loginUser(
    coroutineScope: CoroutineScope,
    email: String,
    password: String,
    onSuccess: (LoginResponse) -> Unit,
    onError: (LoginResponse) -> Unit
){
    coroutineScope.launch {
        try {
            val result = RetrofitInstance.apiService.logUser(email, password)

            if (result.token != null) {
                onSuccess(result)
            } else {
                onError(result)
            }
        } catch (e: HttpException) {
            if (e.code() == 401) {
                onError(LoginResponse(message = "Credenciales inválidas"))
            } else {
                onError(LoginResponse(message = "Error en el servidor"))
            }
        }
    }
}

fun getTagsImpl(
    coroutineScope: CoroutineScope,
    onSuccess: (List<String>) -> Unit,
    onError: (String) -> Unit
) {
//    CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        try {
            val sylabusList = RetrofitInstance.apiService.getSylabus()

            var minAudios = sylabusList.minOfOrNull { it.audios?.size ?: 0 } ?: 0


            val tags = sylabusList
                .filter { it.audios?.size == minAudios }
                .flatMap { it.tags.orEmpty() }
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedBy { it.second }
                .map { it.first }
                .take(8)

            onSuccess(tags)
        } catch (e: Exception) {
            onError("Error: ${e.message}")
        }
    }
}


fun getPhrasesImpl(
    coroutineScope: CoroutineScope,
    name: String,
    onSuccess: (List<String>) -> Unit,
    onError: (String) -> Unit
) {
    coroutineScope.launch {
        try {
            val sylabusList = RetrofitInstance.apiService.getSylabus()

             val filteredText = sylabusList
                 .filter { (it.creador?.nombre == name
                         && it.texto?.isNotEmpty() == true)}
                 .mapNotNull { it.texto }


            onSuccess(filteredText)
        } catch (e: Exception) {
            onError("Error: ${e.message}")
        }
    }
}
