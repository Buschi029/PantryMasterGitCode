package com.prime.pantrymastergitcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.ui.theme.PantryMasterGitCodeTheme
import com.prime.pantrymastergitcode.view.pantry.PantryView
import com.prime.pantrymastergitcode.view.scanner.ScannerView
import com.prime.pantrymastergitcode.view.pantry.PantryViewModel

class MainActivity : ComponentActivity() {
    private val service = OFFAPIService.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantryMasterGitCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    PantryMaster(service = service)
                }
            }
        }
    }
}

@Composable
fun PantryMaster(service: OFFAPIService) {
    val navController = rememberNavController()
    val pantryViewModel = PantryViewModel(service)
    NavHost(navController, startDestination = "PantryView") {
        composable("PantryView") {
            PantryView(
                navController = navController,
                pantryViewModel = pantryViewModel
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PantryMasterGitCodeTheme {
        PantryMaster(service = OFFAPIService.create())
    }
}
