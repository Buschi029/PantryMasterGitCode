package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ScannerView() {
    val tag = "Scanner"
    val context = LocalContext.current
    val scanner = GmsBarcodeScanning.getClient(context)
    val scannerViewModel = viewModel<ScannerViewModel>()
    val product by scannerViewModel.product.collectAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        Text(text = "Add your Product manually")
        TextField(
                value = if (product.kcal != 0) {product.kcal.toString()} else {""},
                label = { Text(text = "Kcal")},
                onValueChange = {
                    scannerViewModel.setProduct(product.copy(kcal = it.toIntOrNull() ?: 0))},
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
                }
        }) {
            Text(text = "Scan")
        }
        Text(text = product.kcal.toString())
    }
}
