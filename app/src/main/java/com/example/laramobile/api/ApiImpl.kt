package com.example.laramobile.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun getTagsImpl(
    coroutineScope: CoroutineScope,
    onSuccess: (List<String>) -> Unit,
    onError: (String) -> Unit
) {
    coroutineScope.launch {
        try {
            val sylabusList = RetrofitInstance.apiService.getSylabus()

            val allTags = sylabusList
                .mapNotNull { it.tags }
                .flatten()
                .distinct()
                .take(8)

            // Pasar las etiquetas procesadas al onSuccess
            onSuccess(allTags)
        } catch (e: Exception) {
            onError("Error: ${e.message}")
        }
    }
}
