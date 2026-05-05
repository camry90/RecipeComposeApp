package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlin.math.roundToInt

@Composable
fun PortionsSlider(
    portionsText: String,
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(Dimens.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
    ) {
        Text(
            text = "ингредиенты".uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "$portionsText",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Slider(
            value = currentPortions.toFloat(),
            onValueChange = { onPortionsChange(it.roundToInt()) },
            valueRange = 1f..12f,
            steps = 10
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PortionsSliderPreview() {
//    RecipeComposeAppTheme() {
//        PortionsSlider(
//            6,
//            {}
//        )
//    }
}