package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun RecipeItem(
    recipe: RecipeUiModel,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(Dimens.paddingMedium),
        onClick = { onRecipeClick(recipe.id, recipe) },
        shape = RoundedCornerShape(Dimens.shapeRadiusCard),
        elevation = CardDefaults.cardElevation(Dimens.elevationCard),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.checker),
                error = painterResource(R.drawable.checker),
                modifier = Modifier.aspectRatio(2.5f)
            )
            Text(
                text = recipe.title,
                modifier = Modifier.padding(Dimens.paddingSmall),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview
@Composable
fun RecipeItemPreview() {

}
