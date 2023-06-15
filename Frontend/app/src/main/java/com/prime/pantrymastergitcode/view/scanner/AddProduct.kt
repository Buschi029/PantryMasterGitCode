package com.prime.pantrymastergitcode.view.scanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.prime.pantrymastergitcode.R
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ProductDTO

@Composable
fun AddProduct(product:ProductDTO, scannerViewModel:ScannerViewModel){
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (product.pictureLink != "") {
                AsyncImage(
                    model = "${product.pictureLink}",
                    contentDescription = "ProductImage",
                    modifier = Modifier
                        .height(100.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier.padding(bottom = 10.dp),
                    value = if (product.name != "") {product.name} else {""},
                    label = { Text(text = "Product Name")},
                    onValueChange = {
                        scannerViewModel.setProduct(product.copy(name = it))},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier.weight(3f),
                            value = if (product.kcal != 0) {
                                product.kcal.toString()
                            } else {
                                ""
                            },
                            label = { Text(text = "Kcal") },
                            onValueChange = {
                                scannerViewModel.setProduct(product.copy(kcal = it.toIntOrNull() ?: 0))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextField(
                            modifier = Modifier.weight(3f),
                            value = if (product.carbohydrates != 0) {
                                product.carbohydrates.toString()
                            } else {
                                ""
                            },
                            label = { Text(text = "Carbohydrates") },
                            onValueChange = {
                                scannerViewModel.setProduct(
                                    product.copy(
                                        carbohydrates = it.toIntOrNull() ?: 0
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier.weight(3f),
                            value = if (product.fat != 0) {
                                product.fat.toString()
                            } else {
                                ""
                            },
                            label = { Text(text = "Kcal") },
                            onValueChange = {
                                scannerViewModel.setProduct(product.copy(fat = it.toIntOrNull() ?: 0))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextField(
                            modifier = Modifier.weight(3f),
                            value = if (product.protein != 0) {
                                product.protein.toString()
                            } else {
                                ""
                            },
                            label = { Text(text = "Protein") },
                            onValueChange = {
                                scannerViewModel.setProduct(
                                    product.copy(
                                        protein = it.toIntOrNull() ?: 0
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier.weight(3f),
                            value = if (product.sugar != 0) {
                                product.sugar.toString()
                            } else {
                                ""
                            },
                            label = { Text(text = "Sugar") },
                            onValueChange = {
                                scannerViewModel.setProduct(product.copy(sugar = it.toIntOrNull() ?: 0))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextField(
                            modifier = Modifier.weight(3f),
                            value = if (product.nutriscore != "") {
                                product.nutriscore
                            } else {
                                ""
                            },
                            label = { Text(text = "Nutriscore") },
                            onValueChange = {
                                scannerViewModel.setProduct(product.copy(nutriscore = it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview(){
    AddProduct(product = ProductDTO(), scannerViewModel = ScannerViewModel())
}
