package com.prime.pantrymastergitcode.view.pantry

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prime.pantrymastergitcode.view.pantry.detailView.DetailView
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import okhttp3.internal.notifyAll
import java.time.format.DateTimeFormatter


@Composable
fun PantryView(pantryViewModel: PantryViewModel) {
    val showProductDetails = pantryViewModel.showProductDetails.collectAsState()
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

data class PantryItem(val id: Long, var name: String, val productName: String,var expirationDate: LocalDate,  var quantity: Int, var quantityUnit: String)

data class PantryProduct(val productCode: Long, var productName: String, var userID: String, var expirationDate: LocalDate, var appendDate: LocalDate, var quantity: Int, var quantityUnit: String)



@Composable
fun PantryList(pantryViewModel: PantryViewModel) {
    var newItem: String by remember { mutableStateOf("") }
    var newQuantity: Int by remember { mutableStateOf(0) }
    var newUnit: String by remember { mutableStateOf("") }
    val sorted by pantryViewModel.sorted.collectAsState()
    val dayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val items2 by pantryViewModel.items1.collectAsState()

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
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Icon(
                imageVector = Icons.Default.Fastfood,
                contentDescription = "Pantry",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .padding(bottom = 10.dp)
            )
        }

        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))



        // Tabelle
        Column(modifier = Modifier.fillMaxWidth()) {
            pantryViewModel.items3.forEachIndexed { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(color = Color.White)
                        .clickable {
                            pantryViewModel.getProductDetails(item.productCode)
                            pantryViewModel.setProductDetails(true)
                        }
                ) {
                    IconButton(
                        onClick = {
                            if (item.quantity > 0) {

                               pantryViewModel.items3[index] = item.copy(quantity = item.quantity.dec())
                                pantryViewModel.updatePantryItem(pantryViewModel.items3[index])


                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Color.Black
                        )
                    }

                    Text(
                        "${item.quantity} ${item.quantityUnit}", style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier

                    )
                    IconButton(
                        onClick = {
                            val updatedItem = item.copy(quantity = item.quantity + 1)
                            val mutableList = pantryViewModel.items.toMutableList()
                            mutableList[index] = updatedItem
                            pantryViewModel.items = mutableList
                            pantryViewModel.updatePantryItem(updatedItem)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.Black
                        )
                    }
                    // Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        item.productName,style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier
                            .weight(1f)
                            .width(100.dp)
                    )
                    Text(
                        item.expirationDate.toJavaLocalDate().format(dayFormatter).toString(),style = TextStyle(fontSize = 10.sp),
                        modifier = Modifier
                            .weight(1f)
                            .width(100.dp)
                    )
                    IconButton(
                        onClick = {
                            pantryViewModel.removeItemFromDatabase(item)
                        }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black
                        )
                    }
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }

        // speichern

        Spacer(modifier = Modifier.height(16.dp))
        Row{
            Button(
                onClick = { },
                modifier = Modifier
                    .width(120.dp)
            ) {
                Text("Save", color = Color.Black)
            }
            Button(
                onClick = {
//                    if(!sorted){
//                        pantryViewModel.unsortedItems = pantryViewModel.items
//                        pantryViewModel.items = pantryViewModel.items.sortedBy { it.expirationDate }
//                        pantryViewModel.setSorted(true)
//                    }else{
//                        pantryViewModel.items = pantryViewModel.unsortedItems
//                        pantryViewModel.setSorted(false)
//
//                    }

                }
            ){
                Text(text = "sortieren")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

    }
}
