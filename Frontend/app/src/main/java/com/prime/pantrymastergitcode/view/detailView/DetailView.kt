package com.prime.pantrymastergitcode.view.detailView

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prime.pantrymastergitcode.R


@Composable
fun DetailView(){
    Card(elevation = 10.dp,
    shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .size(300.dp)
    ) {
        Box() {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            horizontalAlignment = Alignment.End) {
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Icon(Icons.Outlined.Close,
                        contentDescription = "Close",
                    modifier = Modifier
                        .size(100.dp)
                        .fillMaxSize(),
                    tint = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(id = R.drawable.samlepicture),
                    contentDescription = "ProductImage",
                    modifier = Modifier
                        .height(100.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                )
                Text(text = "ProductName", style = TextStyle(fontWeight = FontWeight.Bold))
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
                        Text(text = "Kilocalories")
                        Text(text = "Carbohydrates")
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Fat")
                        Text(text = "Sugar")
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Protein")
                        Text(text = "Nutriscore")
                    }
                }

            }
        }
    }

}

@Preview(showBackground = false)
@Composable
fun DefaultPreview(){
    DetailView()
}
