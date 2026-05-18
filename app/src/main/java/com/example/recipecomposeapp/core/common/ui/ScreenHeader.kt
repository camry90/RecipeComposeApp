package com.example.recipecomposeapp.core.common.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme


@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String,
    showShareButton: Boolean = false,
    onShareClick: () -> Unit = {},
    showFavoriteButton: Boolean = false,
    onFavoriteToggle: () -> Unit = {},
    isFavorite: Boolean = false,
) {
    val heartFull = rememberVectorPainter(
        image = ImageVector.vectorResource(R.drawable.ic_heart)
    )
    val heartEmpty = rememberVectorPainter(
        image = ImageVector.vectorResource(R.drawable.ic_heart_empty)
    )

    Box(
        modifier = Modifier
            .height(Dimens.heightScreenHeader)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium),
            horizontalAlignment = Alignment.End
        ) {
            if (showFavoriteButton) {
                Crossfade(
                    targetState = isFavorite,
                    animationSpec = tween(300),
                    label = "Favorite"
                ) { isCurrentlyFavorite ->
                    Icon(
                        painter = if (isCurrentlyFavorite) heartFull else heartEmpty,
                        contentDescription = "Favourite",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(Dimens.iconSize)
                            .clickable { onFavoriteToggle() }
                    )
                }
            }

            if (showShareButton) {
                Image(
                    painterResource(R.drawable.share_icon),
                    contentDescription = "Share",
                    modifier = Modifier
                        .size(Dimens.iconSize)
                        .clickable { onShareClick() }
                )
            }
        }

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

@Composable
fun ScreenHeader(
    imagePainter: String,
    contentDescription: String,
    title: String,
    showShareButton: Boolean,
    onShareClick: () -> Unit,
    showFavoriteButton: Boolean = false,
    onFavoriteToggle: () -> Unit = {},
    isFavorite: Boolean = false,
) {
    val heartFull = rememberVectorPainter(
        image = ImageVector.vectorResource(R.drawable.ic_heart)
    )
    val heartEmpty = rememberVectorPainter(
        image = ImageVector.vectorResource(R.drawable.ic_heart_empty)
    )

    Box(
        modifier = Modifier
            .height(Dimens.heightScreenHeader)
    ) {
        AsyncImage(
            model = imagePainter,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.checker),
            error = painterResource(R.drawable.checker),
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium),
            horizontalAlignment = Alignment.End
        ) {
            if (showFavoriteButton) {
                Crossfade(
                    targetState = isFavorite,
                    animationSpec = tween(300),
                    label = "Favorite",
                ) { isCurrentlyFavorite ->
                    Icon(
                        painter = if (isCurrentlyFavorite) heartFull else heartEmpty,
                        contentDescription = "Favorite",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(Dimens.iconSize)
                            .clickable { onFavoriteToggle() }
                    )
                }
            }

            if (showShareButton) {
                Image(
                    painterResource(R.drawable.share_icon),
                    contentDescription = "Share",
                    modifier = Modifier
                        .size(Dimens.iconSize)
                        .clickable { onShareClick() }
                )
            }
        }


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
                color = MaterialTheme.colorScheme.onSurface,
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
            title = "категории",
            showShareButton = true,
            onShareClick = {},
            showFavoriteButton = true
        )
    }
}