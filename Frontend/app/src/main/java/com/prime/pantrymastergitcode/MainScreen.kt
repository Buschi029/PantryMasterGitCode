package com.prime.pantrymastergitcode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.ui.theme.Timberwolf
import com.prime.pantrymastergitcode.view.bottomBar.BottomBarScreen
import com.prime.pantrymastergitcode.view.bottomBar.BottomNavGraph

@Composable
fun MainScreen(service:OFFAPIService, scanner: GmsBarcodeScanner) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {

        },
        bottomBar = { BottomBar(navController = navController) },
        content = { innerPadding ->
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                BottomNavGraph(navController = navController, service = service, scanner = scanner)
            }
        }
    )
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Scanner,
        BottomBarScreen.ShoppingList,
        BottomBarScreen.Pantry,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen->
            AddItem(screen = screen, currentDestination = currentDestination , navController = navController )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
           Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "Navigation Icon")
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = Color(0x70000000),
        selectedContentColor = Color.Black,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}
