package com.prime.pantrymastergitcode

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.ui.theme.Timberwolf
import com.prime.pantrymastergitcode.view.bottomBar.BottomBarScreen
import com.prime.pantrymastergitcode.view.bottomBar.BottomNavGraph

@Composable
fun MainScreen(service:OFFAPIService) {
    val navController = rememberNavController()
    Scaffold (
        topBar = {
            // Hier kannst du den Inhalt der Top Bar platzieren, aber da du sie ausblenden möchtest,
            // kannst du sie einfach leer lassen oder null setzen.
        },
        bottomBar = { BottomBar(navController = navController)}
    ){
    BottomNavGraph(navController = navController, service = service)
    }
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
