package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ScannerView(scannerViewModel: ScannerViewModel) {
    val tag = "Scanner"
    val context = LocalContext.current
    val scanner = GmsBarcodeScanning.getClient(context)
    val product by scannerViewModel.product.collectAsState()
    val pantryProduct by scannerViewModel.pantryProduct.collectAsState()
    val dayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val dateDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 100.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Add your Product manually")
        TextField(
            modifier = Modifier.padding(bottom = 10.dp),
            value = if (product.name != "") {pantryProduct.productName} else {""},
            label = { Text(text = "Product Name")},
            onValueChange = {
                scannerViewModel.setPantryProduct(pantryProduct.copy(productName = it))},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        Row(modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
        ) {
            TextField(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(2f),
                value = if (pantryProduct.quantity != 0) {pantryProduct.quantity.toString()} else {""},
                label = { Text(text = "Qty")},
                onValueChange = {
                    if(it != ""){
                        scannerViewModel.setPantryProduct(pantryProduct.copy(quantity = it.toInt()))
                    }else{
                        scannerViewModel.setPantryProduct(pantryProduct.copy(quantity = 0))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier
                .weight(1f))
            TextField(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(7f),
                value = if (pantryProduct.quantityUnit != "") {pantryProduct.quantityUnit} else {""},
                label = { Text(text = "Unit")},
                onValueChange = {
                    scannerViewModel.setPantryProduct(pantryProduct.copy(quantityUnit = it))},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }
        Button(onClick = {
            dateDialogState.show()
        }) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(
                    Icons.Default.CalendarToday,
                    contentDescription = "Calendar",
                    modifier = Modifier
                        .size(30.dp)
                        .fillMaxSize(),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))

                Column() {
                    Text(text = "Mindesthaltbarkeitsdatum")
                    Text("${pantryProduct.expirationDate.toJavaLocalDate().format(dayFormatter)}")
                }
            }

        }

        Text(text = "OR")
        Row() {
            Button(onClick = {
                scanner.startScan()
                    .addOnSuccessListener { barcode ->
                        scannerViewModel.getProductFromAPI(barcode.rawValue.toString().toLong())
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
                Text(text = "Scan the Barcode of the Product")
            }
            Button(onClick = { scannerViewModel.postProductToPantry(pantryProduct) }) {
                Text(text = "PostProduct")
            }
        }
        MaterialDialog(
            dialogState = dateDialogState,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            buttons = {
                positiveButton(text = "Okay")
                negativeButton(text = "Cancel")
            }
        ) {
            datepicker(
                initialDate = pantryProduct.expirationDate.toJavaLocalDate(),
            ){
                scannerViewModel.setPantryProduct(pantryProduct.copy(expirationDate = it.toKotlinLocalDate()))
            }
        }
        
    }
}
