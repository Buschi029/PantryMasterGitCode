package com.prime.pantrymastergitcode.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// Festlegung, von Farbpaletten
private val DarkColorPalette = darkColors(

    primary = mainColor,
    primaryVariant = secondaryColor,
    secondary = secondaryColor
)

private val LightColorPalette = lightColors(

    primary = mainColor,
    primaryVariant = secondaryColor,
    secondary = secondaryColor
)

// Festlegung von weiteren optischen Elementen
@Composable
fun PantryMasterGitCodeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    // Farbliche Gestaltung der SystemBars
    val colors = if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = mainColor
        )
        DarkColorPalette
    } else {
        systemUiController.setSystemBarsColor(
            color = mainColor
        )
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

