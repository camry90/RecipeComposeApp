package com.example.recipecomposeapp.ui.categories

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
import coil.compose.AsyncImage
import com.example.recipecomposeapp.DESCRIPTIONS_MAX_LINES
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.categories.model.CategoryUiModel
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun CategoryItem(
    category: CategoryUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.paddingSmall),
        shape = RoundedCornerShape(Dimens.shapeRadiusCard),
        elevation = CardDefaults.cardElevation(Dimens.elevationCard),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = category.title,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.checker),
            error = painterResource(R.drawable.checker),
            modifier = Modifier.aspectRatio(1.2f)
        )
        Text(
            text = category.title.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(Dimens.paddingSmall)
        )
        Text(
            text = category.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(Dimens.paddingSmall),
            maxLines = DESCRIPTIONS_MAX_LINES
        )
    }
}