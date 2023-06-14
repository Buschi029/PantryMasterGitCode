package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ScannerView(scannerViewModel: ScannerViewModel) {
    val tag = "Scanner"
    val context = LocalContext.current
    val scanner = GmsBarcodeScanning.getClient(context)
    var scannerTag by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        Text(text = "Add your Product manually")
        TextField(
                value = scannerViewModel.product.kcal.toString(),
                onValueChange = {
                    scannerViewModel.product.kcal = scannerViewModel.product.kcal.copyit.toInt()
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        Text(text = "OR")

        Button(onClick = {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    scannerViewModel.getProduct(barcode.rawValue.toString().toLong())
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
        Text(text = scannerViewModel.product.kcal.toString())
    }
}
