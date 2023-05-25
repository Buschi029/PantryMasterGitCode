package com.prime.pantrymastergitcode.view.pantry

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prime.pantrymastergitcode.api.OFFAPIService

@Composable
fun PantryView(
    navController: NavController,
    pantryViewModel: PantryViewModel
) {
    Text(Text = pantryViewModel.product.name){
        onClick
    }
}

@Preview
@Composable
fun DefaultPreview() {
    PantryView(navController = rememberNavController(),
    pantryViewModel = PantryViewModel(service = OFFAPIService.create())
    )
}
