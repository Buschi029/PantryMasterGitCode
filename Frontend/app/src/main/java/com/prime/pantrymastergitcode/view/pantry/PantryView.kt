package com.prime.pantrymastergitcode.view.pantry

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PantryView(
    navController: NavController
) {
    Text(text = "toDo")
}

@Preview
@Composable
fun DefaultPreview() {
    PantryView(navController = rememberNavController())
}