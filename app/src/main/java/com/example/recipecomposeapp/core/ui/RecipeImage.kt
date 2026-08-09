package com.example.recipecomposeapp.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipecomposeapp.R

@Composable
fun RecipeImage(imageUrl: String, contentDescription: String, contentScale: ContentScale, modifier: Modifier = Modifier) {
    Box() {
        var isLoading by remember { mutableStateOf(true) }
        val context = LocalContext.current
        val imageRequest = remember(imageUrl) {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .size(200, 200)
                .crossfade(true)
                .build()
        }
        AsyncImage(
            model = imageRequest,
            contentDescription = contentDescription,
            contentScale = contentScale,
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(R.drawable.img_error),
            onLoading = { isLoading = true },
            onSuccess = { isLoading = false },
            onError =  { isLoading = false },
            modifier = modifier
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}