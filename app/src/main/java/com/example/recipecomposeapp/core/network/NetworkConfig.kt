package com.example.recipecomposeapp.core.network

import com.example.recipecomposeapp.core.network.api.RecipeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object NetworkConfig {

    val json = Json { ignoreUnknownKeys = true }
    val contentType = "application/json".toMediaType()

    val retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val apiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)
    const val BASE_URL = "https://recipes.androidsprint.ru/api/"
}