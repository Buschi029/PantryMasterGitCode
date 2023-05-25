package com.prime.pantrymastergitcode.view
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prime.pantrymastergitcode.ui.theme.PantryMasterGitCodeTheme
import com.prime.pantrymastergitcode.view.pantry.PantryView
import com.prime.pantrymastergitcode.view.HomeScreen
import com.prime.pantrymastergitcode.view.ScannerScreen
import com.prime.pantrymastergitcode.view.ShoppingListScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen() {
   Text(text = "Home")
}
