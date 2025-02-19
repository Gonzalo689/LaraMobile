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

            var minAudios = sylabusList.minOfOrNull { it.audios?.size ?: 0 } ?: 0

            var tags: List<String>

            do {
                tags = sylabusList
                    .filter { it.audios?.size == minAudios }
                    .flatMap { it.tags.orEmpty() }
                    .groupingBy { it }
                    .eachCount()
                    .toList()
                    .sortedBy { it.second }
                    .map { it.first }
                    .take(8)



                minAudios++


            } while (tags.size <= 8)

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

            val allTags = sylabusList
                .mapNotNull { it.tags }
                .flatten()
                .groupBy { it }
                .mapValues { it.value.size }
                .toList()
                .sortedBy { it.second }
                .map { it.first }
                .take(8)

            onSuccess(allTags)
        } catch (e: Exception) {
            onError("Error: ${e.message}")
        }
    }
}
