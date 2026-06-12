package com.example.recipecomposeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    private var deepLink by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
            try {
                connection.connect()
                val data = connection.getInputStream().bufferedReader().use { it.readText() }
                Log.i("!!!", "Body: $data" )
                val json = Json { ignoreUnknownKeys = true }
                val deserializeData = json.decodeFromString<List<CategoryDto>>(data)
                Log.i("!!!", "Количество категорий: ${deserializeData.size}")
                Log.i("!!!", "Названия категорий: ${deserializeData.joinToString { category -> category.title }}")
                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            } catch (e: Exception) {
                Log.i("!!!", "Ошибка: ${e.message}")
            } finally {
                connection.disconnect()
            }
        }
        thread.start()

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