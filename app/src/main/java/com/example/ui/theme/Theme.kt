package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SaffronDarkPrimary,
    onPrimary = SaffronDarkOnPrimary,
    primaryContainer = SaffronDarkContainer,
    onPrimaryContainer = SaffronDarkOnContainer,
    secondary = DivineGold,
    onSecondary = SaffronDarkOnPrimary,
    background = SaffronDarkBackground,
    onBackground = SaffronDarkOnBackground,
    surface = SaffronDarkSurface,
    onSurface = SaffronDarkOnSurface,
    surfaceVariant = SaffronDarkSurfaceVariant,
    onSurfaceVariant = SaffronDarkOnSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = SaffronLightPrimary,
    onPrimary = SaffronLightOnPrimary,
    primaryContainer = SaffronLightContainer,
    onPrimaryContainer = SaffronLightOnContainer,
    secondary = SaffronSecondary,
    onSecondary = SaffronLightOnPrimary,
    background = SaffronLightBackground,
    onBackground = SaffronLightOnBackground,
    surface = SaffronLightSurface,
    onSurface = SaffronLightOnSurface,
    surfaceVariant = SaffronLightSurfaceVariant,
    onSurfaceVariant = SaffronLightOnSurfaceVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep intentional sacred saffron theme for spiritual app
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
