package com.prime.pantrymastergitcode.view.pantry.detailView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.prime.pantrymastergitcode.R
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.view.pantry.PantryViewModel

@Composable
fun DetailView(pantryViewModel: PantryViewModel) {
    val product by pantryViewModel.product.collectAsState()
    val loading by pantryViewModel.loading.collectAsState()

    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .size(300.dp)
    ) {
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Button(
                    onClick = { pantryViewModel.setProductDetails(false) },
                    modifier = Modifier
                        .size(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(10.dp)
                            .fillMaxSize(),
                        tint = Color.White
                    )
                }
            }
            if (!loading) {
                Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator()
                }
            } else {
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
                        Text(
                            text = "${product.name}",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Kilocalories: ${product.kcal}")
                                Text(text = "Carbohydrates: ${product.carbohydrates}")
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Fat: ${product.fat}")
                                Text(text = "Sugar: ${product.sugar}")
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Protein: ${product.protein}")
                                Text(text = "Nutriscore: ${product.nutriscore}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    DetailView(PantryViewModel(OFFAPIService.create()))
}
