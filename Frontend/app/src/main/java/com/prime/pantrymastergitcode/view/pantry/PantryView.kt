package com.prime.pantrymastergitcode.view.pantry


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.view.scanner.ScannerViewModel

@Composable
fun PantryView(
    navController: NavController,
    scannerViewModel: ScannerViewModel
) {

}

@Preview
@Composable
fun DefaultPreview() {
    PantryView(navController = rememberNavController(),
    scannerViewModel = ScannerViewModel(service = OFFAPIService.create())
    )
}
