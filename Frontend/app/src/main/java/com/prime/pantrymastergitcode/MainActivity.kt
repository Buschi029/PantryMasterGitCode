package com.prime.pantrymastergitcode

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
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
                    val options = GmsBarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                            Barcode.FORMAT_EAN_13,
                            Barcode.FORMAT_EAN_8,
                            Barcode.FORMAT_UPC_A,
                            Barcode.FORMAT_ITF,
                            Barcode.FORMAT_CODE_128
                        ).build()
                    val context = LocalContext.current
                    val scanner = GmsBarcodeScanning.getClient(context,options)
                    val moduleInstallRequest =
                        ModuleInstallRequest.newBuilder()
                            .addApi(scanner) //Add the scanner client to the module install request
                            .build()

                    val moduleInstallClient = ModuleInstall.getClient(context)
                    MainScreen(service = service, scanner)
                    moduleInstallClient
                        .installModules(moduleInstallRequest)
                        .addOnFailureListener {
                            Log.e("MainActivity", "Error installing modules, Scanner may not be available", it)
                        }
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




