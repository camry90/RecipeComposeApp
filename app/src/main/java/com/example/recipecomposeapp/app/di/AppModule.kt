package com.example.recipecomposeapp.app.di

import android.content.Context
import com.example.recipecomposeapp.BuildConfig
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.network.NetworkConfig.BASE_URL
import com.example.recipecomposeapp.data.network.api.RecipeApiService
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.data.repository.RecipesRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import kotlin.jvm.java


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Provides
    @Singleton
    fun provideRecipesApiService(): RecipeApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(RecipeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipesDatabase(
        @ApplicationContext context: Context
    ): RecipesDatabase {
        return RecipesDatabase.getDatabase(context)
    }
}