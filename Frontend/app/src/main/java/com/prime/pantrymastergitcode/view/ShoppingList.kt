package com.prime.pantrymastergitcode.view
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.*
import java.time.format.TextStyle

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
    ) { Text(
            text = "Shopping List",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 12.dp)
    )
        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) { TextField(
            value = newQuantity,
            onValueChange = { newQuantity = it },
            placeholder = { Text("Quantity") },
            modifier = Modifier.width(100.dp)
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
                modifier = Modifier.width(60.dp)
                    .height(50.dp)
            ) {
                Text("Add")
            }
        }
        //Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Column(modifier = Modifier.fillMaxWidth()) {
            items.forEach { item ->
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
                            .clickable{isCheckedState.value = !isCheckedState.value}
                            .clip(RoundedCornerShape(8.dp))
                            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                    )

                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}

