package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.prime.pantrymastergitcode.api.dto.ProductDTO

@Composable
fun ScannerView(scannerViewModel: ScannerViewModel) {
    val tag = "Scanner"
    val context = LocalContext.current
    val scanner = GmsBarcodeScanning.getClient(context)
    var scannerTag by remember { mutableStateOf("")}
    Button(onClick = {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                scannerViewModel.getProduct(barcode.toString().toLong())
                scannerTag = scannerViewModel.product.nutriscore
            }
            .addOnCanceledListener {
                // Task canceled
                Log.i(tag, "Scanning has been cancelled")
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                Log.e(tag, e.toString())
                Toast.makeText(context, "An Error occurred", Toast.LENGTH_SHORT).show()
                scannerTag = e.toString()
            }
    }) {
        Text(text = "Scan")
    }
    /*Text(text = scannerViewModel.product.nutriscore)*/
    Column() {
        Button(onClick = { scannerViewModel.getProduct(code = 90162480)
            Log.i("Scannerview", "mehtodenaufruf gestartet")}) {
            Text(text = "Test")
        }
        Text(text = scannerViewModel.product.kcal.toString())
    }

}

