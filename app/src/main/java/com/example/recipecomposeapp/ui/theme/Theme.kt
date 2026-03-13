package com.example.recipecomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    error = AccentColorDark,
    tertiary = AccentBlueDark,
    background = BackgroundColorDark,
    onPrimary = SurfaceColorDark,
    surface = SurfaceColorDark,
    outline = DividerColorDark,
    onSurface = TextPrimaryColorDark,
    onSecondary = TextSecondaryColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    tertiaryContainer = SliderTrackColorDark,

    )

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    error = AccentColor,
    tertiary = AccentBlue,
    background = BackgroundColor,
    onPrimary = SurfaceColor,
    surface = SurfaceColor,
    outline = DividerColor,
    onSurface = TextPrimaryColor,
    onSecondary = TextSecondaryColor,
    surfaceVariant = SurfaceVariantColor,
    tertiaryContainer = SliderTrackColor,
)

@Composable
fun RecipeComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = recipesAppTypography,
        content = content
    )
}