package com.faizdev.mykiosk.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faizdev.mykiosk.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockItem(name: String, stockCount: Int, description: String, onClick : (nama : String, stok: Int, desc: String) -> Unit, onDelete : () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(start = 16.dp, end = 16.dp, top = 13.dp)
            ,
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0XFFEEF5FF)),
        onClick = {
            onClick(
                name  ,
                stockCount ,
                description ,
            )

        },


        ) {
        Row(
            modifier = Modifier
                .padding(start = 11.dp)
                .fillMaxSize()
                .weight(1f), verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = "${name}",
                    color = Color.Black,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier

                        .padding(start = 8.dp),
                )

                Text(
                    text = "Stok : ${stockCount}",
                    color = Color.Black,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Thin,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 5.dp),
                )

                Text(
                    text = "${description}",
                    color = Color.Black,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Thin,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 6.dp),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 11.dp),

                    onClick = {
                        onDelete()
                    }) {

                    Icon(
                        tint = Color.Red,
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete"
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun Pv() {

}