package com.example.recipecomposeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.recipecomposeapp.core.navigation.AppNavHost
import com.example.recipecomposeapp.core.network.NetworkConfig
import com.example.recipecomposeapp.core.network.api.RecipeApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {

    private var deepLink by mutableStateOf<Intent?>(null)
    val json = Json { ignoreUnknownKeys = true }
    val apiService = NetworkConfig.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch {
            try {
                val categories = apiService.getCategories()

                coroutineScope {
                    categories.map { category ->
                        async {
                            try {
                                val recipes = apiService.getRecipesByCategory(category.id)
                                Log.i(
                                    "Recipe",
                                    "название категории: ${category.title}, количество рецептов: ${recipes.size}"
                                )
                            } catch (e: Exception) {
                                Log.i("Recipe", "название категории: ${category.title}, Ошибка: $e")
                            }
                        }
                    }.awaitAll()
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
}