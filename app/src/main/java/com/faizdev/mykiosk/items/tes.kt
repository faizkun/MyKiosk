package com.faizdev.mykiosk.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faizdev.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(

) {


    Scaffold(

        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Setting",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "",
                            modifier = Modifier.size(55.dp)
                        )
                    }
                },

                colors =
                TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.inversePrimary
                )
            )
        }


    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inversePrimary)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(87.dp)
                        .padding(horizontal = 16.dp)
                        .padding(top = 12.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Dark Mode",
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            color = MaterialTheme.colorScheme.onSurface


                        )


                    }


                }


            }
        }
    }
}

@Preview
@Composable
fun pv() {
    SettingScreen()
}
