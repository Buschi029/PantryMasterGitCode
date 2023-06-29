package com.prime.pantrymastergitcode.view.bottomBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.view.HomeScreen
import com.prime.pantrymastergitcode.view.bottomBar.BottomBarScreen
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListScreen
import com.prime.pantrymastergitcode.view.pantry.PantryView
import com.prime.pantrymastergitcode.view.pantry.PantryViewModel
import com.prime.pantrymastergitcode.view.scanner.ScannerView
import com.prime.pantrymastergitcode.view.scanner.ScannerViewModel
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, service: OFFAPIService, scanner: GmsBarcodeScanner) {
    val pantryViewModel = PantryViewModel(service = service)
    val scannerViewModel = ScannerViewModel(service)
    val shoppingListViewModel = ShoppingListViewModel()

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Scanner.route) {
            ScannerView(scannerViewModel, scanner)
        }
        composable(route = BottomBarScreen.ShoppingList.route) {
            ShoppingListScreen(shoppingListViewModel)
        }
        composable(route = BottomBarScreen.Pantry.route) {
            PantryView(pantryViewModel)
        }
    }
}
