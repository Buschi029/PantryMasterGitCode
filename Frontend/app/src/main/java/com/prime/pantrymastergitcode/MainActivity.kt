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
import com.prime.pantrymastergitcode.ui.theme.PantryMasterGitCodeTheme
import com.prime.pantrymastergitcode.view.pantry.PantryView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantryMasterGitCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    PantryMaster()
                }
            }
        }
    }
}

@Composable
fun PantryMaster() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "inputView") {
        composable("PantryView") {
            PantryView(
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PantryMasterGitCodeTheme {
        PantryMaster()
    }
}
