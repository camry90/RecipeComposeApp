package com.example.recipecomposeapp.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun ScreenHeader(imagePainter: Painter, contentDescription: String, title: String) {
    Box(
        modifier = Modifier
            .height(Dimens.heightScreenHeader)
            .statusBarsPadding()
    ) {
        Image(
            painter = imagePainter ,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Surface(
            Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.paddingMedium),
            shape = RoundedCornerShape(Dimens.shapeRadiusCard),
            color = MaterialTheme.colorScheme.surface

        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimens.paddingHeaderText)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenHeaderPreview() {
    RecipeComposeAppTheme() {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.categories_header),
            contentDescription = "Categories",
            title = "категории"
        )
    }
}