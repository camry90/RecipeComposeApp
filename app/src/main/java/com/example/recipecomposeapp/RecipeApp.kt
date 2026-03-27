package com.example.recipecomposeapp

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeApp() {
    RecipeComposeAppTheme {
        Scaffold { paddingValues ->
            Text(
                text = "Recipe App",
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppPreview() {
    RecipeApp()
}