package com.faizdev.mykiosk.items

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faizdev.ui.theme.poppins


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkItem(
    name: String,
    stockCount: Int,
    description: String,
    onClick: (nama: String, stok: Int, desc: String) -> Unit,
    onDelete: () -> Unit,

    ) {

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(start = 16.dp, end = 16.dp, top = 13.dp)
            .combinedClickable(
                onClick = {

                },
                onLongClick = {
                    showAlertDialog = true
                },

                ),

        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),


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
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier

                        .padding(start = 8.dp),
                )

                Text(
                    text = "Stok : ${stockCount}",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Thin,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 5.dp),
                )

                Text(
                    text = "${description}",
                    color = MaterialTheme.colorScheme.onSurface,
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

            }
        }
    }

    if (showAlertDialog){
        AlertDialog(
            title = {
                Text(text = "Anda yakin ingin Hapus")
            },
            onDismissRequest = {showAlertDialog = false}
            , confirmButton = {
                TextButton(onClick = {
                    onClick(
                        name,
                        stockCount,
                        description,
                    )
                    Toast.makeText(context, "Hapus", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Hapus")
                }


            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAlertDialog = false
                    }) {
                    Text(text = "Batal")
                }


            }

        )
    }

}