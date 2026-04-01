package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.ScreenHeader
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.checker),
            contentDescription = "Recipes",
            title = "рецепты",
        )
        Text("Скоро здесь будет список рецептов")
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    RecipeComposeAppTheme {
        RecipesScreen()
    }
}