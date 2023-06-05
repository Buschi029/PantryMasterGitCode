package com.prime.pantrymastergitcode.view.pantry


import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Remove
import androidx.compose.ui.draw.clip

@Composable
fun PantryViewTestScreen() {
    PantryList()
}

data class PantryItem(val name: String, var quantity: Int, var date: String)


@Composable
fun PantryList() {
    val items = remember { mutableStateListOf<PantryItem>() }
    var newItem: String by remember { mutableStateOf("") }
    var newQuantity: Int by remember { mutableStateOf(0) }
    var newDate: String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { Row(
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
            modifier = Modifier.size(40.dp)
                .padding(bottom = 10.dp)
        )
    }

        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) { TextField(
            value = if (newQuantity != 0) newQuantity.toString() else "",
            onValueChange = { newQuantity = it.toIntOrNull() ?: 0 },
            placeholder = { Text("Quantity") },
            modifier = Modifier.width(100.dp)
                .height(50.dp)
                .weight(1f)
        )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                placeholder = { Text("Element") },
                modifier = Modifier
                    .weight(1f)
                    .width(120.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = newDate,
                onValueChange = { newDate = it },
                placeholder = { Text("Date") },
                modifier = Modifier
                    .weight(1f)
                    .width(80.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    items.add(PantryItem(newItem, newQuantity, newDate))
                    newItem = ""
                    newQuantity = 0
                    newDate = ""
                },
                modifier = Modifier.width(60.dp)
                    .height(50.dp)
            ) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)

        // Tabelle

        Column(modifier = Modifier.fillMaxWidth()) {
            items.forEachIndexed{ index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                    //.padding(vertical = 4.dp)
                    //.padding(horizontal = 4.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (item.quantity > 0) {
                                items[index] = item.copy(quantity = item.quantity - 1)
                            }
                        },
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Color.Black
                        )
                    }

                    Text(
                        item.quantity.toString(),
                        modifier = Modifier
                        //.width(80.dp)
                        //.padding(end = 8.dp)
                        //.weight(1f)

                    )
                    IconButton(
                        onClick = {
                            items[index] = item.copy(quantity = item.quantity + 1)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.Black
                        )
                    }
                    //Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        item.name,
                        modifier = Modifier.weight(1f)
                            .width(100.dp)

                    )
                    Text(
                        item.date,
                        modifier = Modifier.weight(1f)
                            .width(100.dp)

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

        // speichern

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { var saveButtonClicked = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .width(120.dp)
        ) {
            Text("Save")
        }

    }
}

