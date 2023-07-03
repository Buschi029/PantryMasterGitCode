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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prime.pantrymastergitcode.R
import com.prime.pantrymastergitcode.ui.theme.mainColor
import com.prime.pantrymastergitcode.ui.theme.secondaryColor

@Composable
fun ShoppingListScreen(shoppingListViewModel: ShoppingListViewModel) {

    // GET-Request beim Start der App
    LaunchedEffect(Unit) {
        shoppingListViewModel.getItemsFromDatabase()
    }
    // Aufruf der Shopping List
    ShoppingList(shoppingListViewModel)
}

// Datenklasse eines ShoppingItems
data class ShoppingItem(val name: String, var quantity: String,
                        var quantityType: String)

// Die tatsächliche Shopping List
@Composable
fun ShoppingList(shoppingListViewModel: ShoppingListViewModel) {
    var newItem: String by remember { mutableStateOf("") }
    var newQuantity: Int by remember { mutableIntStateOf(0) }
    var newQuantityType: String by remember { mutableStateOf("") }
    val loading by shoppingListViewModel.loading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Überschrift
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.shopping_list),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .testTag("Headline")
            )
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "ShoppingList",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .padding(bottom = 10.dp)
                    .testTag("HeadlineIcon")
            )

        }

        Divider(color = Color.LightGray, thickness = 3.dp)
        Spacer(modifier = Modifier.height(12.dp))

        if (loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("ProgressIndicator")
                )
            }
        } else {
            // Eingabefenster
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Eingabe der Quantität
                TextField(
                    value = if (newQuantity != 0) newQuantity.toString() else "",
                    onValueChange = { newQuantity = it.toIntOrNull() ?: 0 },
                    placeholder = { Text(stringResource(R.string.quantity_shoppingList), style = TextStyle(fontSize = 12.sp)) },
                    modifier = Modifier
                        .weight(4f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(secondaryColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Eingabe der Unit
                TextField(
                    value = newQuantityType,
                    onValueChange = { newQuantityType = it },
                    placeholder = { Text(stringResource(R.string.unit_shoppingList), style = TextStyle(fontSize = 12.sp)) },
                    modifier = Modifier
                        .weight(3f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(secondaryColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Eingabe des Elements
                TextField(
                    value = newItem,
                    onValueChange = { newItem = it },
                    placeholder = { Text(stringResource(R.string.element_shoppingList), style = TextStyle(fontSize = 12.sp)) },
                    modifier = Modifier
                        .weight(5f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(secondaryColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        // Hinzufügen der Einträge zur Datenbank
                        shoppingListViewModel.addItemsToDatabase(
                            newItem,
                            newQuantity,
                            newQuantityType
                        )
                        newItem = ""
                        newQuantity = 0
                        newQuantityType = ""
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .height(50.dp)
                        .background(color = mainColor)
                ) {
                    Text(stringResource(R.string.add_shoppingList), color = Color.Black, style = TextStyle(fontSize = 12.sp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)
            // Schleife, welche die Einträge aus der Datenbank in Form einer Tabelle darstellt
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ShoppingList")
            ) {
                shoppingListViewModel.items.forEach { item ->
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
                            modifier = Modifier.weight(2f)
                        )

                        IconButton(
                            onClick = {
                                shoppingListViewModel.removeItemFromDatabase(
                                    item.productName
                                )
                            }
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
        }
    }
}
