package com.prime.pantrymastergitcode.view.shoppingList

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import io.ktor.client.*

@Composable
fun ShoppingListScreen() {
    //Text(text = "ShoppingList")
    ShoppingList()
}

data class ShoppingItem(val name: String, var quantity: String, var isChecked: Boolean = false)


@Composable
fun ShoppingList() {
    val items = remember { mutableStateListOf<ShoppingItem>() }
    var newItem: String by remember { mutableStateOf("") }
    var newQuantity: String by remember { mutableStateOf("") }

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
                modifier = Modifier.size(40.dp)
                    .padding(bottom = 10.dp)
            )
        }


        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))



        Row(
            modifier = Modifier.fillMaxWidth()
        ) { TextField(
            value = newQuantity,
            onValueChange = { newQuantity = it },
            placeholder = { Text("Quantity") },
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
        )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                placeholder = { Text("Element") },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    items.add(ShoppingItem(newItem, newQuantity))
                    newItem = ""
                    newQuantity = ""
                },
                modifier = Modifier
                    .width(60.dp)
                    .height(50.dp)
            ) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Column(modifier = Modifier.fillMaxWidth()) {
            items.forEachIndexed { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        item.quantity,

                        modifier = Modifier
                            .width(80.dp)
                            .padding(end = 8.dp)
                            //.height(40.dp)
                            //.weight(1f)
                            //.clip(RoundedCornerShape(8.dp))
                            //.background(Color(0xFFF3F3F3))
                            //.border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                            //.align(Alignment.CenterVertically),
                        //textAlign = TextAlign.Center,





                    )
                    //Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        item.name,
                        modifier = Modifier.weight(1f)
                    )

                    val isCheckedState = remember { mutableStateOf(item.isChecked) }

                    Checkbox(
                        checked = isCheckedState.value,
                        onCheckedChange = { isChecked -> isCheckedState.value = isChecked},
                        modifier = Modifier
                            .clickable { isCheckedState.value = !isCheckedState.value }
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                    IconButton(
                        onClick = {
                            items.removeAt(index)
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
        Button(
            onClick = { var saveButtonClicked = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(120.dp)
        ) {
            Text("Save")
        }
    }
}

fun getData() {

}

