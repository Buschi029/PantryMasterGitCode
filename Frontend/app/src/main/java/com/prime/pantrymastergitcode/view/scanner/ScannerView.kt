package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.prime.pantrymastergitcode.view.permissions.CameraPermission


@Composable
fun ScannerView(){
    val TAG = "Scanner"
    val context = LocalContext.current
    val scanner = GmsBarcodeScanning.getClient(context)
    var scannerTag by remember {mutableStateOf("")}
    var productName by remember {mutableStateOf("")}
    var bestBefore by remember {mutableStateOf("")}
    var qantity by remember {mutableStateOf("")}
    Box(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment =Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CameraPermission()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Add New Product", fontSize = 30.sp)
            Text(text = scannerTag)
            Row() {
                TextField(value = productName, onValueChange = { productName = it })
                TextField(value = bestBefore, onValueChange = { productName = it })
                TextField(value = productName, onValueChange = { productName = it })
            }

            Button(onClick = {
                scanner.startScan()
                    .addOnSuccessListener { barcode ->
                        scannerTag = barcode.toString()
                    }
                    .addOnCanceledListener {
                        // Task canceled
                        Log.i(TAG, "Scanning has been cancelled")
                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        Log.e(TAG, e.toString())
                        Toast.makeText(context, "An Error occurred", Toast.LENGTH_SHORT).show()
                        scannerTag = e.toString()
                    }
            }) {
                Text(text = "Scan")

            }
        }

    }
}

