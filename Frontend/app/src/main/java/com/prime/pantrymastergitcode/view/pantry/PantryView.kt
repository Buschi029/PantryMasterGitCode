package com.prime.pantrymastergitcode.view.pantry

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prime.pantrymastergitcode.view.pantry.detailView.DetailView
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import com.prime.pantrymastergitcode.ui.theme.mainColor
import com.prime.pantrymastergitcode.ui.theme.secondaryColor

// View, welche die digitale Speisekammer beinhaltet
@Composable
fun PantryView(pantryViewModel: PantryViewModel) {
    val showProductDetails = pantryViewModel.showProductDetails.collectAsState()

    // Durchführung einer GET-Anfrage beim Start des Views
    LaunchedEffect(Unit) {
        pantryViewModel.getPantryItemsFromDatabase()
    }
    Box {
        PantryList(pantryViewModel)
        if (showProductDetails.value) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                DetailView(pantryViewModel)
            }
        }
    }
}

// Darstellung der digitalen Speisekammer
@Composable
fun PantryList(pantryViewModel: PantryViewModel) {
    val dayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val context = LocalContext.current
    val loading by pantryViewModel.loadingPantry.collectAsState()

    // Überschrift der Seite
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Pantry List",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .testTag("Headline")
            )
            Icon(
                imageVector = Icons.Default.Fastfood,
                contentDescription = "Pantry",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .padding(bottom = 10.dp)
                    .testTag("HeadlineIcon")
            )
        }

        Divider(color = Color.LightGray, thickness = 3.dp)

        if (loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("ProgressIndicator")
                )
            }else{
            
            // Schleife zur Anzeige der Pantry Items in einer Tabelle
            Column(modifier = Modifier
                .testTag("PantryList") 
                .fillMaxWidth()) {
                pantryViewModel.getPantryList().forEachIndexed { index, item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(horizontal = 1.dp)
                            .fillMaxWidth()
                            .clickable {
                                if (item.productCode != 0L) {
                                    pantryViewModel.getProductDetails(
                                        item.productCode,
                                        item.productName
                                    )
                                    pantryViewModel.setProductDetails(true)
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "There are no Product Details available",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                    ) {
                        IconButton(
                            // Erhöhung der Quantität
                            onClick = {
                                if (item.quantity > 0) {
                                    pantryViewModel.updatePantryItem(item.copy(quantity = item.quantity.dec()), index = index)
                                }
                            }, modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint = mainColor,
                                modifier = Modifier.width(20.dp)
                            )
                        }
                        IconButton(
                            // Verringerung der Quantität
                            onClick = {
                                pantryViewModel.updatePantryItem(item.copy(quantity = item.quantity.inc()), index = index)
                            }, modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Increase",
                                tint = mainColor,
                                modifier = Modifier
                                    .width(20.dp)
                                    .padding(end = 2.dp)
                            )
                        }
                        Text(
                            "${item.quantity} ${item.quantityUnit}", style = TextStyle(fontSize = 14.sp),
                            modifier = Modifier
                                .width(50.dp)
                                .weight(2f)
                        )
                        Text(
                            item.productName,style = TextStyle(fontSize = 14.sp),
                            modifier = Modifier
                                .weight(5f)
                                .width(100.dp)
                        )
                        Text(
                            item.expirationDate.toJavaLocalDate().format(dayFormatter).toString(),style = TextStyle(fontSize = 12.sp),
                            modifier = Modifier
                                .weight(3f)
                                .width(100.dp)
                                .padding(start = 4.dp)
                        )
                        IconButton(
                            onClick = {
                                pantryViewModel.removeItemFromDatabase(item)
                            }, modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = secondaryColor
                            )
                        }
                    }
                    Divider(color = secondaryColor, thickness = 1.dp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .weight(2f),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        // Sortierung der Liste
                        pantryViewModel.sortList()
                    }
                ) {
                    Text(text = "Sort list", color = Color.Black)
                }
            }
        }
    }
}
