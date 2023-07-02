package com.prime.pantrymastergitcode.view.pantry.detailView

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.prime.pantrymastergitcode.MainViewModel
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
            .height(330.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                IconButton(
                    onClick = { pantryViewModel.setProductDetails(false) },
                    modifier = Modifier
                        .size(35.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(50.dp)
                            .fillMaxSize(),
                        tint = MaterialTheme.colors.primary
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
                        Column(modifier = Modifier.weight(5f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                            if (product.pictureLink != "") {
                                AsyncImage(
                                    model = "${product.pictureLink}",
                                    contentDescription = "ProductImage",
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(
                                            RoundedCornerShape(10.dp)
                                        )
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${pantryViewModel.checkAvailability(product.name)}",
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(3f)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Kilocalories: ${pantryViewModel.checkAvailability(product.kcal)}")
                                Text(text = "Carbohydrates: ${pantryViewModel.checkAvailability(product.carbohydrates)}")
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Fat: ${pantryViewModel.checkAvailability(product.fat)}")
                                Text(text = "Sugar: ${pantryViewModel.checkAvailability(product.sugar)}")
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Protein: ${pantryViewModel.checkAvailability(product.protein)}")
                                Text(text = "Nutriscore: ${pantryViewModel.checkAvailability(product.nutriscore)}")
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
    DetailView(PantryViewModel(OFFAPIService.create(), MainViewModel()))
}
