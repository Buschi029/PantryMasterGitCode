package com.prime.pantrymastergitcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.ui.theme.PantryMasterGitCodeTheme


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
                    MainScreen(service = service)
                }
            }
        }
    }
}

//@Composable
//fun PantryMaster(service: OFFAPIService) {
//    val navController = rememberNavController()
//    val pantryViewModel = PantryViewModel(service)
//    NavHost(navController, startDestination = "PantryView") {
//        composable("PantryView") {
//            PantryView(
//                navController = navController,
//                pantryViewModel = pantryViewModel
//            )
//        }
//    }
//
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    PantryMasterGitCodeTheme {
//        PantryMaster(service = OFFAPIService.create())
//    }
//}




