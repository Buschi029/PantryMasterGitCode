package com.prime.pantrymastergitcode.view.bottomBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.prime.pantrymastergitcode.MainViewModel
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListScreen
import com.prime.pantrymastergitcode.view.pantry.PantryView
import com.prime.pantrymastergitcode.view.pantry.PantryViewModel
import com.prime.pantrymastergitcode.view.scanner.ScannerView
import com.prime.pantrymastergitcode.view.scanner.ScannerViewModel
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListViewModel

// Methode zur Navigation innerhalb der Navigationsleiste
@Composable
fun BottomNavGraph(navController: NavHostController, service: OFFAPIService, scanner: GmsBarcodeScanner, mainViewModel: MainViewModel) {
    val pantryViewModel = PantryViewModel(service = service, mainViewModel = mainViewModel)
    val scannerViewModel = ScannerViewModel(service = service, mainViewModel = mainViewModel)
    val shoppingListViewModel = ShoppingListViewModel(service = service, mainViewModel = mainViewModel)
    val mainViewModel = mainViewModel

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Pantry.route
    ) {

        composable(route = BottomBarScreen.Scanner.route) {
            ScannerView(scannerViewModel, scanner)
        }
        composable(route = BottomBarScreen.Pantry.route) {
            PantryView(pantryViewModel)
        }
        composable(route = BottomBarScreen.ShoppingList.route) {
            ShoppingListScreen(shoppingListViewModel)
        }
    }
}
