package com.faizdev.mykiosk.screen.dashboard

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faizdev.mykiosk.data.source.remote.StokData
import com.faizdev.mykiosk.items.StockItem
import com.faizdev.mykiosk.screen.destinations.BookmarkScreenDestination
import com.faizdev.mykiosk.state.BottomSheetState
import com.faizdev.mykiosk.ui.theme.poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {

    val getAllStock = dashboardViewModel.getAllStock.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        dashboardViewModel.connectToRealtime()
    }

    val updateBottomSheet =
        mutableStateOf(
            BottomSheetState(id = 0, nama = "", stock = 0 , deskripsi = "", boolean = false)

        )


    val sheetState = rememberModalBottomSheetState()

    var insertBottomSheet by remember {
        mutableStateOf(false)
    }
    var totalStock by remember{
        mutableStateOf("")
    }



    Scaffold(

        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Hi, Pengguna",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "",

                        )
                    }
                },

                colors =
                TopAppBarDefaults.topAppBarColors(
                    Color(
                        (0xFF86B6F6)
                    ),
                )
            )
        },



        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    insertBottomSheet = true
                },
                containerColor = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Stock",
                    tint = Color.White,
                )
            }


        },


        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xffB4D4FF))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)

            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(123.dp)
                        .background(Color(0xFF86B6F6))
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .height(128.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(21.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0XFFEEF5FF)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 13.dp)
                            .padding(top = 40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 42.dp)
                        ) {
                            Text(
                                text = "Total Barang",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = totalStock,
                                fontFamily = poppins,
                                fontSize = 16.sp
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 42.dp)
                                .clickable {
                                    navigator.navigate(BookmarkScreenDestination)
                                }
                        ) {
                            Text(
                                text = "Bookmark",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                            Icon(
                                imageVector = Icons.Default.Bookmark, contentDescription = "",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)

                            )
                        }
                    }
                }
            }

            getAllStock.value.DisplayResult(
                onLoading = {
                  Box(
                      modifier = Modifier
                          .fillMaxSize(),
                      contentAlignment = Alignment.Center
                  ) {
                      LinearProgressIndicator()
                  }
                },
                onSuccess = {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        content = {
                            totalStock = if (it.isNotEmpty()) {
                                it.size.toString()
                            } else {
                                "0"
                            }
                            items(it) { list ->
                                StockItem(
                                    name = list.name,
                                    stockCount = list.stock,
                                    description = list.description,
                                    onClick = { nama, stock, desc ->

                                        updateBottomSheet.value = BottomSheetState(id = list.id!!, nama = nama ,stock = stock, deskripsi = desc , boolean = true)

                                    },
                                    onDelete = {
                                        dashboardViewModel.deleteStock(list.id!!)
                                    }
                                )
                            }
                        })
                }, onError = {

                }
            )


        }


        if (insertBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { insertBottomSheet = false },
                sheetState = sheetState
            ) {

                var enableTextName by remember {
                    mutableStateOf("")
                }
                var enableTextDesc by remember {
                    mutableStateOf("")
                }
                var enableCount by remember {
                    mutableStateOf("")
                }


                OutlinedTextField(
                    value = enableTextName,
                    label = { Text(text = "Nama") },
                    onValueChange = {
                        enableTextName = it
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )


                Spacer(modifier = Modifier.size(7.dp))
                OutlinedTextField(
                    value = enableCount,
                    label = { Text(text = "Stok") },
                    onValueChange = {
                        enableCount = it
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )


                Spacer(modifier = Modifier.size(7.dp))
                OutlinedTextField(
                    value = enableTextDesc,
                    label = { Text(text = "Deskripsi") },
                    onValueChange = {
                        enableTextDesc = it
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                )

                Spacer(modifier = Modifier.size(13.dp))
                Button(
                    onClick = {

                        val stock = StokData(
                            name = enableTextName,
                            stock = enableCount.toInt(),
                            description = enableTextDesc,
                        )

                        dashboardViewModel.insertStock(stock)
                        insertBottomSheet = false
                    },
                    modifier = Modifier
                        .padding(horizontal = 125.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF86B6F6)
                    )
                ) {
                    Text(text = "Simpan")

                }
                Spacer(modifier = Modifier.size(98.dp))


            }
        }

        if ( updateBottomSheet.value.boolean) {
            ModalBottomSheet(
                onDismissRequest = {  updateBottomSheet.value =  updateBottomSheet.value.copy(
                    boolean = false
                ) },
                sheetState = sheetState
            ) {

                var enableTextName by remember {
                    mutableStateOf( updateBottomSheet.value.nama)
                }
                var enableTextDesc by remember {
                    mutableStateOf( updateBottomSheet.value.deskripsi)
                }
                var enableCount by remember {
                    mutableStateOf( updateBottomSheet.value.stock)
                }


                OutlinedTextField(
                    value = enableTextName,
                    label = { Text(text = "Nama") },
                    onValueChange = {
                        enableTextName = it
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )


                Spacer(modifier = Modifier.size(7.dp))
                OutlinedTextField(
                    value = enableCount.toString(),
                    label = { Text(text = "Stok") },
                    onValueChange = {
                        enableCount = it.toInt()
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )


                Spacer(modifier = Modifier.size(7.dp))
                OutlinedTextField(
                    value = enableTextDesc,
                    label = { Text(text = "Deskripsi") },
                    onValueChange = {
                        enableTextDesc = it
                    },
                    shape = RoundedCornerShape(11.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                )

                Spacer(modifier = Modifier.size(13.dp))
                Button(
                    onClick = {
                        dashboardViewModel.updateStock(id =  updateBottomSheet.value.id ,nama = enableTextName, stok = enableCount , desc = enableTextDesc )
                        updateBottomSheet.value =  updateBottomSheet.value.copy(boolean = false)
                    },
                    modifier = Modifier
                        .padding(horizontal = 125.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF86B6F6)
                    )
                ) {
                    Text(text = "Simpan")

                }
                Spacer(modifier = Modifier.size(98.dp))


            }
        }




    }

//    Scaffold(
//
//
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(it)
//                .fillMaxSize(),
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(220.dp)
//
//            ) {
//
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//                    .background(Color(0xFF86B6F6))
//            )
//
//            Card(
//
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 13.dp)
//                        .padding(top = 40.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//
//                }
//
//            }
//
//        }
//    }

    DisposableEffect(Unit){
        onDispose { dashboardViewModel.leaveRealtimeChannel() }
    }

}


@Preview
@Composable
fun Pv() {

}

