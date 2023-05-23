package com.prime.pantrymastergitcode.view.Pantry

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PantryView(
    navController : NavController
){

}

@Preview
@Composable
fun DefaultPreview(){
    PantryView(navController = rememberNavController())
}
