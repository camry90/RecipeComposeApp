package com.example.recipecomposeapp.core.network

import android.os.Build
import androidx.core.os.BuildCompat
import com.example.recipecomposeapp.BuildConfig
import com.example.recipecomposeapp.core.network.api.RecipeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NetworkConfig {

    val json = Json { ignoreUnknownKeys = true }
    val contentType = "application/json".toMediaType()

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            Level.BODY
        } else
        {
            Level.NONE
        }
    }

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()

    val apiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)
    const val BASE_URL = "https://recipes.androidsprint.ru/api/"
}