package com.example.recipecomposeapp.ui.recipes.utils

import android.content.Context
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.recipecomposeapp.Screen

fun shareRecipe(
    context: Context,
    recipeId: Int,
    recipeTitle: String,
) {
    val shareLink = Screen.RecipeDetails.createRecipeDeepLink(recipeId)
    val shareText = "Попробуй этот рецепт: $recipeTitle\n$shareLink"

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_INTENT, shareText)
    }
    context.startActivity(
        Intent.createChooser(intent, "Поделиться рецептом")
    )
}