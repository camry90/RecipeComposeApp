package com.example.recipecomposeapp.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.data.FavoriteDataStoreManager
import com.example.recipecomposeapp.ui.theme.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val context = LocalContext.current
    val favoritePrefs = remember { FavoriteDataStoreManager(context) }
    val favoriteCount by favoritePrefs
        .getFavoriteCountFlow()
        .collectAsState(initial = 0)

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall),
        modifier = Modifier
            .navigationBarsPadding()
            .padding(Dimens.paddingSmall)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.tertiary
            ),
            shape = RoundedCornerShape(Dimens.shapeRadiusButton),
            onClick = onCategoriesClick,
        ) {
            Text(
                text = "категории".uppercase(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Button(
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(Dimens.shapeRadiusButton),
            onClick = onFavoriteClick
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingIcon),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "избранное".uppercase(),
                    style = MaterialTheme.typography.titleMedium
                )

                BadgedBox(
                    badge = {
                        if (favoriteCount > 0) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.surface
                            ) {
                                Text("$favoriteCount")
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_heart_empty),
                        contentDescription = "empty_heart",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    RecipeComposeAppTheme() {
        BottomNavigation(
            onCategoriesClick = {},
            onFavoriteClick = {}
        )
    }
}