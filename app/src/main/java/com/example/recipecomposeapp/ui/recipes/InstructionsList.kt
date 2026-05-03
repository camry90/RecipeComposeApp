package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun InstructionsList(
    instructions: String,
    modifier: Modifier = Modifier,
) {
    val methods = instructions.split("\n").filter { it.isNotBlank() }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.paddingMedium),
        shape = RoundedCornerShape(Dimens.shapeRadiusCard),
        elevation = CardDefaults.cardElevation(Dimens.elevationCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column {
            methods.forEachIndexed { index, method ->
                InstructionItem(index + 1, method)
                if (index < methods.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
                    )
                }
            }
        }
    }
}
