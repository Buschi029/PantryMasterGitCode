package com.prime.pantrymastergitcode

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.prime.pantrymastergitcode.api.OFFAPIService

//Methode zum starten der zu testenden Composable-Funktion
@OptIn(ExperimentalMaterialApi::class)
fun ComposeContentTestRule.launchAPP(service: OFFAPIService) {
    setContent {
        val scanner = GmsBarcodeScanning.getClient(LocalContext.current)
        MainScreen(service = service, scanner = scanner)
    }
}
