package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.prime.pantrymastergitcode.MainViewModel
import com.prime.pantrymastergitcode.R
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.ui.theme.secondaryColor
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate

// View, welche den Produktscanner beinhaltet
@Composable
fun ScannerView(scannerViewModel: ScannerViewModel, scanner: GmsBarcodeScanner) {
    val tag = "Scanner"
    val context = LocalContext.current
    val pantryProduct by scannerViewModel.pantryProduct.collectAsState()
    val dayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val dateDialogState = rememberMaterialDialogState()
    val focusManager = LocalFocusManager.current
    val loading by scannerViewModel.loading.collectAsState()
    val loaded by scannerViewModel.loaded.collectAsState()
    val detailProduct by scannerViewModel.product.collectAsState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .weight(4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(id = R.drawable.groceries),
                            contentDescription = "Groceries",
                            modifier = Modifier
                                .height(150.dp)
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                        )
                        Text(
                            text = "Add your Product",
                            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    if (loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.background)
                        )
                        CircularProgressIndicator()
                    } else if (loaded) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.background)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.End
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        scannerViewModel.setPantryProduct(PantryItemDTO())
                                        scannerViewModel.setProduct(detailProduct.copy(pictureLink = ""))
                                        scannerViewModel.setLoaded(false)
                                    }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                        AsyncImage(
                            model = detailProduct.pictureLink,
                            contentDescription = "ProductImage",
                            modifier = Modifier
                                .height(150.dp)
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )

                        )
                    }
                }
            }
            // Eingabefeld für den Produktnamen
            Column(
                modifier = Modifier
                    .weight(4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .background(color = secondaryColor)
                        .fillMaxWidth(),
                    value = if (pantryProduct.productName != "") {
                        pantryProduct.productName
                    } else {
                        ""
                    },
                    label = { Text(text = "Product Name") },
                    onValueChange = {
                        scannerViewModel.setPantryProduct(pantryProduct.copy(productName = it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                // Eingabefelder für die Quantität und Einheit des Produkts
                Row {
                    TextField(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .background(color = secondaryColor)
                            .weight(1f),
                        value = if (pantryProduct.quantity != 0) {
                            pantryProduct.quantity.toString()
                        } else {
                            ""
                        },
                        label = { Text(text = "Quantity") },
                        onValueChange = {
                            if (it != "") {
                                scannerViewModel.setPantryProduct(pantryProduct.copy(quantity = it.toInt()))
                            } else {
                                scannerViewModel.setPantryProduct(pantryProduct.copy(quantity = 0))
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                    Spacer(
                        modifier = Modifier
                            .width(8.dp)
                    )
                    TextField(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .background(color = secondaryColor)
                            .weight(1f),
                        value = if (pantryProduct.quantityUnit != "") {
                            pantryProduct.quantityUnit
                        } else {
                            ""
                        },
                        label = { Text(text = "Unit") },
                        onValueChange = { newValue ->
                            if (newValue.length <= 3) {
                                scannerViewModel.setPantryProduct(pantryProduct.copy(quantityUnit = newValue))
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                }

                // Button zur Auswahl des Mindesthaltbarkeitsdatums
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        dateDialogState.show()
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = "Calendar",
                            modifier = Modifier
                                .size(30.dp)
                                .fillMaxSize()
                                .weight(2f),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Column(
                            modifier = Modifier
                                .weight(7f),
                            verticalArrangement = Arrangement.Center) {
                            Text(
                                text = "Expiration Date",
                                style = TextStyle(color = Color.Black)
                            )
                            Text(
                                pantryProduct.expirationDate.toJavaLocalDate().format(dayFormatter),
                                style = TextStyle(color = Color.Black)
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .weight(2f)
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Button, welcher den Barcodescanner aufruft
            Button(
                modifier = Modifier.weight(3f),
                onClick = {
                    scanner.startScan()
                        .addOnSuccessListener { barcode ->
                            scope.launch {
                                var failed = scope.async {
                                    scannerViewModel.getProductFromAPI(
                                        barcode.rawValue.toString().toLong()
                                    )
                                }
                                if (failed.await()) {
                                    Log.e("ScannerViewModel", failed.await().toString())
                                    Toast.makeText(
                                        context,
                                        "Could not load Product Info",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                        .addOnCanceledListener {
                            Log.i(tag, "Scanning has been cancelled")
                        }
                        .addOnFailureListener { e ->
                            // Task failed with an exception
                            Log.e(tag, e.toString())
                            Toast.makeText(context, "An Error occurred", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            ) {
                Text(
                    text = "Scan Barcode",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
            }
            if (pantryProduct.productName != "" && pantryProduct.quantity != 0 && pantryProduct.quantityUnit != "") {
                Spacer(modifier = Modifier.weight(1f))

                // Button zum Hinzufügen des Produkts in die digitale Speisekammer
                Button(
                    modifier = Modifier
                        .weight(3f),
                    onClick = {
                        scope.launch {
                            var failed = scope.async {
                                scannerViewModel.addProductToPantry(pantryProduct)
                            }
                            if (failed.await()) {
                                Log.e("ScannerViewModel", failed.await().toString())
                                Toast.makeText(
                                    context,
                                    "Could not add Product to Pantry",
                                    Toast.LENGTH_LONG
                                ).show()
                            }else{
                                Toast.makeText(
                                    context,
                                    "Product successfully added to pantry!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                ) {
                    Text(text = "Add Product", color = Color.Black, fontSize = 16.sp)
                }
            }
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
            allowedDateValidator = {
                it.isAfter(LocalDate.now().minusDays(1))
            }
        ) {
            scannerViewModel.setPantryProduct(pantryProduct.copy(expirationDate = it.toKotlinLocalDate()))
        }
    }
}
