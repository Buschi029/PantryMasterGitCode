package com.prime.pantrymastergitcode.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    //primary = Purple200,
    //primaryVariant = Purple700,
    //secondary = Teal200

    primary = Timberwolf,
    primaryVariant = Ebony,
    secondary = Olivine

)

private val LightColorPalette = lightColors(
    //primary = Purple500,
    //primaryVariant = Purple700,
    //secondary = Teal200

    primary = Timberwolf,
    primaryVariant = Ebony,
    secondary = Olivine

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
            color = Color.Transparent
        )
        DarkColorPalette
    } else {
        systemUiController.setSystemBarsColor(
            color = Color.White
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

