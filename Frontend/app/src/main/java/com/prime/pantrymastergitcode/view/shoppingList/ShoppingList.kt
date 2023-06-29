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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.prime.pantrymastergitcode.R
import com.prime.pantrymastergitcode.ui.theme.Ebony
import com.prime.pantrymastergitcode.ui.theme.Olivine
import com.prime.pantrymastergitcode.ui.theme.Timberwolf
import com.prime.pantrymastergitcode.view.pantry.PantryItem
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.ContentType.Application.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListViewModel
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.statement.HttpResponse


@Composable
fun ShoppingListScreen(shoppingListViewModel: ShoppingListViewModel) {

    ShoppingList(shoppingListViewModel)
}

data class ShoppingItem(val name: String, var quantity: String,
                        var quantityType: String, var isChecked: Boolean = false)

@Composable
fun ShoppingList(shoppingListViewModel: ShoppingListViewModel) {
    /*
        val items = remember { mutableStateListOf<ShoppingItem>() }
        var newItem: String by remember { mutableStateOf("") }
        var newQuantity: String by remember { mutableStateOf("") }
        var newQuantityType: String by remember { mutableStateOf("") }

     */
    /*
        val items = shoppingListViewModel.items
        var newItem = shoppingListViewModel.newItem
        var newQuantity = shoppingListViewModel.newQuantity
        var newQuantityType = shoppingListViewModel.newQuantityType
        */

    var response: String by remember{ mutableStateOf("") }

    var newItem: String by remember { mutableStateOf("") }
    var newQuantity: Int by remember { mutableStateOf(0) }
    var newQuantityType: String by remember { mutableStateOf("") }



    /*
    var items by shoppingListViewModel.items.collectAsState()
    var newItem by shoppingListViewModel.newItem.collectAsState()
    var newQuantity by shoppingListViewModel.newQuantity.collectAsState()
    var newQuantityType by shoppingListViewModel.newQuantityType.collectAsState()

     */


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

        // TEXT
        Text(text = response)



        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = if (newQuantity != 0) newQuantity.toString() else "",
                onValueChange = { newQuantity = it.toIntOrNull() ?: 0 },
                placeholder = { Text("Quantity", style = TextStyle(fontSize = 14.sp)) },
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
                placeholder = { Text("Type", style = TextStyle(fontSize = 14.sp)) },
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
                placeholder = { Text("Element", style = TextStyle(fontSize = 14.sp)) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Timberwolf)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { },
                modifier = Modifier
                    .width(60.dp)
                    .height(50.dp)
            ) {
                Text("Add", color = Color.Black)
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
                            // shoppingListViewModel.removeItem(item)
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
            onClick = { shoppingListViewModel.getItemsFromDatabase() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(120.dp)

        ) {
            Text("Get", color = Color.Black)
        }
    }
}

// Daten speichern

fun saveShoppingList(items: List<ShoppingItem>) {


    /*
    val json = Json.encodeToString(items)

    // Send the JSON data to the backend endpoint
    val url = "http:// 82.165.114.121"
    val client = HttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    if (response.statusCode() == 200) {
        // Successfully saved the shopping list
        println("Shopping list saved!")
    } else {
        // Error occurred while saving the shopping list
        println("Failed to save the shopping list. Status code: ${response.statusCode()}")
    }
    */

}
