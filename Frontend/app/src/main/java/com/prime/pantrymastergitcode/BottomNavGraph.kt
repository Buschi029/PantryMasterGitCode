package com.prime.pantrymastergitcode

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.NavHostController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prime.pantrymastergitcode.view.HomeScreen
import com.prime.pantrymastergitcode.view.ScannerScreen
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListScreen
import com.prime.pantrymastergitcode.view.pantry.PantryView
import com.prime.pantrymastergitcode.view.pantry.PantryViewTestScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Scanner.route) {
            ScannerScreen()
        }
        composable(route = BottomBarScreen.ShoppingList.route) {
            ShoppingListScreen()
        }
        composable(route = BottomBarScreen.Pantry.route) {
            PantryViewTestScreen()
        }
    }
}
