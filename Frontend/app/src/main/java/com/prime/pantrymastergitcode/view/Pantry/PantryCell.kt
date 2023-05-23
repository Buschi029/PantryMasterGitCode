package com.prime.pantrymastergitcode.view.Pantry

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PantryCell(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Row() {
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantryCellPreview(){
    PantryCell()
}