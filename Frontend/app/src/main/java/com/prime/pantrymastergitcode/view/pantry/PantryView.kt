package com.prime.pantrymastergitcode.view.pantry


import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prime.pantrymastergitcode.api.OFFAPIService
import java.io.Console

@Composable
fun PantryView(
    navController: NavController,
    pantryViewModel: PantryViewModel
) {
    Column() {
        Text(text = pantryViewModel.product.product.name)
        Button(onClick = {pantryViewModel.getProduct()}){
            Text(text = "get Product Name")
        }
    }


}

@Preview
@Composable
fun DefaultPreview() {
    PantryView(navController = rememberNavController(),
    pantryViewModel = PantryViewModel(service = OFFAPIService.create())
    )
}
