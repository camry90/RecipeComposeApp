package com.example.recipecomposeapp

import android.R.attr.data
import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    private val okHttpClient = OkHttpClient()
    private var deepLink by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    val json = Json { ignoreUnknownKeys = true }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        threadPool.execute {
            try {
                val request = Request.Builder()
                    .url("https://recipes.androidsprint.ru/api/category")
                    .build()

                val response = okHttpClient.newCall(request).execute()
                val body = response.body.string()
                val categories = json.decodeFromString<List<CategoryDto>>(body)

                for (category in categories) {
                    threadPool.execute {
                        val threadName = Thread.currentThread().name
                        try {
                            val request = Request.Builder()
                                .url("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
                                .build()
                            val response = okHttpClient.newCall(request).execute()
                            val body = response.body.string()
                            val recipes = json.decodeFromString<List<RecipeDto>>(body)
                            Log.i(
                                "Pool",
                                "Имя потока: $threadName, название категории: ${category.title}, количество рецептов: ${recipes.size}"
                            )
                        } catch (e: Exception) {
                            Log.i("Pool", "Имя потока: $threadName, название категории: ${category.title}, Ошибка: $e")
                        }
                    }
                }
                Log.i("!!!", "Количество категорий: ${categories.size}")
                Log.i(
                    "!!!",
                    "Названия категорий: ${categories.joinToString { category -> category.title }}"
                )
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("!!!", "Ошибка: ${e.message}")
            }
        }


        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        intent?.data?.let { uri ->
            deepLink = intent
        }

        enableEdgeToEdge()
        setContent {
            RecipeApp(deepLinkIntent = deepLink)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLink = intent
        }
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }
}