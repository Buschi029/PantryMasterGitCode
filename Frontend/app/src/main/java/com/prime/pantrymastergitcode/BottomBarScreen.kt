package com.prime.pantrymastergitcode

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource


sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector,
    val title: String
)
{
    object Home : BottomBarScreen("home", Icons.Default.Home, "Home")
    object Pantry : BottomBarScreen("pantry", Icons.Default.Fastfood, "Pantry")
    object ShoppingList : BottomBarScreen("shoppingList", Icons.Default.ShoppingCart, "Shopping")
    object Scanner: BottomBarScreen("scanner", Icons.Default.QrCodeScanner, "Scanner")

}
