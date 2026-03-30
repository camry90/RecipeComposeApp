package com.example.recipecomposeapp.ui.categories


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.components.ScreenHeader
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoriesScreen() {
    Column() {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.categories_header),
            contentDescription = "Categories",
            title = "категории".uppercase()
        )
        Text("Спиок категорий: ")
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme() {
        CategoriesScreen()
    }
}