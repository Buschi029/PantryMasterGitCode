package com.prime.pantrymastergitcode.view.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

// Initialisierung der Variablen des BottomBarScreens
sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector,
    val title: String
)

// Festlegung der Namen und Icons in der Navigationsleiste
{
    object Pantry : BottomBarScreen("pantry", Icons.Default.Fastfood, "Pantry")
    object ShoppingList : BottomBarScreen("shoppingList", Icons.Default.ShoppingCart, "Shopping")
    object Scanner: BottomBarScreen("scanner", Icons.Default.QrCodeScanner, "Scanner")
}
