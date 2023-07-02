package com.prime.pantrymastergitcode.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(

    primary = mainColor,
    primaryVariant = secondaryColor,
    secondary = secondaryColor

)

private val LightColorPalette = lightColors(

    primary = mainColor,
    primaryVariant = secondaryColor,
    secondary = secondaryColor

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)




@Composable
fun PantryMasterGitCodeTheme(


    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

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

