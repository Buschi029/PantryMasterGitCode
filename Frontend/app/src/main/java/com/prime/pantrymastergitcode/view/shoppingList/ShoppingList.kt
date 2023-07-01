package com.prime.pantrymastergitcode.view.shoppingList

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import com.prime.pantrymastergitcode.ui.theme.Timberwolf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListScreen(shoppingListViewModel: ShoppingListViewModel) {

    LaunchedEffect(Unit) {
        shoppingListViewModel.getItemsFromDatabase()
    }

    ShoppingList(shoppingListViewModel)
}

data class ShoppingItem(val name: String, var quantity: String,
                        var quantityType: String)

@Composable
fun ShoppingList(shoppingListViewModel: ShoppingListViewModel) {

    var response: String by remember{ mutableStateOf("") }

    var newItem: String by remember { mutableStateOf("") }
    var newQuantity: Int by remember { mutableStateOf(0) }
    var newQuantityType: String by remember { mutableStateOf("") }

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
                text = "Shopping List",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "ShoppingList",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .padding(bottom = 10.dp)
            )

        }

        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))



        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = if (newQuantity != 0) newQuantity.toString() else "",
                onValueChange = { newQuantity = it.toIntOrNull() ?: 0 },
                placeholder = { Text("Quantity", style = TextStyle(fontSize = 12.sp)) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Timberwolf)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = newQuantityType,
                onValueChange = { newQuantityType = it },
                placeholder = { Text("Type", style = TextStyle(fontSize = 12.sp)) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Timberwolf)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                placeholder = { Text("Element", style = TextStyle(fontSize = 12.sp)) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Timberwolf)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    shoppingListViewModel.addItemsToDatabase(newItem, newQuantity, newQuantityType)
                    newItem = ""
                    newQuantity = 0
                    newQuantityType = ""
                },
                modifier = Modifier
                    .width(60.dp)
                    .height(50.dp)
            ) {
                Text("Add", color = Color.Black, style = TextStyle(fontSize = 12.sp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Column(modifier = Modifier.fillMaxWidth()) {
            shoppingListViewModel.items.forEachIndexed { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        "${item.quantity} ${item.quantityUnit}",

                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)

                    )
                    Text(
                        item.productName,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            shoppingListViewModel.removeItemFromDatabase(
                                item.productName,
                                item.quantity,
                                item.quantityUnit
                            )
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
        Spacer(modifier = Modifier.height(16.dp))

    }
}
