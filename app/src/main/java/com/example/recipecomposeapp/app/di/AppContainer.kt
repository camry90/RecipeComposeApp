package com.example.recipecomposeapp.app.di

import android.content.Context
import com.example.recipecomposeapp.BuildConfig
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.network.NetworkConfig
import com.example.recipecomposeapp.data.network.NetworkConfig.BASE_URL
import com.example.recipecomposeapp.data.network.api.RecipeApiService
import com.example.recipecomposeapp.data.repository.RecipesRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class AppContainer(context: Context) {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            Level.BODY
        } else {
            Level.NONE
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val json = Json { ignoreUnknownKeys = true }

    private val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()

    val recipesApi: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    private val recipesDatabase = RecipesDatabase.getDatabase(context)

    val recipesRepository = RecipesRepositoryImpl(
        apiService = recipesApi,
        database = recipesDatabase,
    )
}