package com.faizdev.mykiosk.screen.dashboard

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faizdev.mykiosk.data.kotpref.Kotpref
import com.faizdev.mykiosk.data.local.entity.Bookmark
import com.faizdev.mykiosk.data.source.remote.StokData
import com.faizdev.mykiosk.items.StockItem
import com.faizdev.mykiosk.screen.destinations.BookmarkScreenDestination
import com.faizdev.mykiosk.screen.destinations.DashboardScreenDestination
import com.faizdev.mykiosk.screen.destinations.LoginScreenDestination
import com.faizdev.mykiosk.screen.destinations.SettingScreenDestination
import com.faizdev.mykiosk.state.BottomSheetState
import com.faizdev.ui.theme.poppins
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

    LaunchedEffect(Unit) {
        dashboardViewModel.connectToRealtime()
    }

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    val searchText by dashboardViewModel.searchText.collectAsState()
    val isSearching by dashboardViewModel.isSearching.collectAsState()

    val context = LocalContext.current

    val updateBottomSheet =
        mutableStateOf(
            BottomSheetState(id = 0, nama = "", stock = 0, deskripsi = "", boolean = false)
        )


    val sheetState = rememberModalBottomSheetState()

    var insertBottomSheet by remember {
        mutableStateOf(false)
    }
    var totalStock by remember {
        mutableStateOf("")
    }





    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Hi, ${Kotpref.username ?: "" }",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(SettingScreenDestination){
                                popUpTo(LoginScreenDestination.route){
                                    inclusive = true
                                }
                                launchSingleTop = true

                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "",
                        )
                    }
                },
                colors =
                TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.inversePrimary
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
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .height(128.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(21.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
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
                                .padding(horizontal = 24.dp)
                        ) {
                            Text(
                                text = "Total Barang",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = totalStock,
                                fontFamily = poppins,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .clickable {
                                    navigator.navigate(BookmarkScreenDestination)
                                }
                        ) {
                            Text(
                                text = "Bookmark",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                imageVector = Icons.Default.Bookmark, contentDescription = "",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            SearchBar(
                query = searchText,
                onQueryChange = { dashboardViewModel.onSearchTextChange(it) },
                onSearch = { dashboardViewModel.onSearchTextChange(it) },
                active = isSearching,
                onActiveChange = { dashboardViewModel.onToogleSearch() },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                placeholder = {
                    Text(
                        text = "Cari Barang",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Thin,
                        fontFamily = poppins

                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
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
                                items(it.filter { it.name!!.contains(searchText, true) }) { list ->
                                    StockItem(
                                        name = list.name,
                                        stockCount = list.stock,
                                        description = list.description,
                                        onClick = { nama, stock, desc ->

                                            updateBottomSheet.value = BottomSheetState(
                                                id = list.id!!,
                                                nama = nama,
                                                stock = stock,
                                                deskripsi = desc,
                                                boolean = true
                                            )

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
                onSuccess = { list ->
                    if (list.isEmpty()){
                        totalStock = "0"
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Anda belum menambahkan barang apapun",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.surface

                                )
                        }
                    } else{
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            content = {
                                totalStock = list.size.toString()
                                items(list) { list ->
                                    StockItem(
                                        name = list.name,
                                        stockCount = list.stock,
                                        description = list.description,
                                        onClick = { nama, stock, desc ->

                                            updateBottomSheet.value = BottomSheetState(
                                                id = list.id!!,
                                                nama = nama,
                                                stock = stock,
                                                deskripsi = desc,
                                                boolean = true
                                            )

                                        },
                                        onDelete = {
                                            dashboardViewModel.deleteStock(list.id!!)
                                        }
                                    )
                                }
                            })

                    }

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

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Tambah Barang",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold


                )


                Spacer(modifier = Modifier.size(12.dp))
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

                        if (enableTextName.isEmpty() || enableCount.isEmpty() || enableTextDesc.isEmpty()){
                            Toast.makeText(context, "Isi terlebih dahulu", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val stock = StokData(
                            name = enableTextName,
                            stock = enableCount.toInt(),
                            description = enableTextDesc,
                            userId = Kotpref.id!!
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


        if (updateBottomSheet.value.boolean) {
            ModalBottomSheet(
                onDismissRequest = {
                    updateBottomSheet.value = updateBottomSheet.value.copy(
                        boolean = false
                    )
                },
                sheetState = sheetState
            ) {

                var enableTextName by remember {
                    mutableStateOf(updateBottomSheet.value.nama)
                }
                var enableTextDesc by remember {
                    mutableStateOf(updateBottomSheet.value.deskripsi)
                }
                var enableCount by remember {
                    mutableStateOf(updateBottomSheet.value.stock)
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Update Barang",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold


                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {


                    IconButton(
                        onClick = {
                            val bookmark = Bookmark(
                                name = updateBottomSheet.value.nama,
                                stock = updateBottomSheet.value.stock,
                                description = updateBottomSheet.value.deskripsi,
                                id = updateBottomSheet.value.id
                            )

                            dashboardViewModel.insertBookmark(bookmark)

                            Toast.makeText(context, "Ditambahkan ke Bookmark", Toast.LENGTH_SHORT)
                                .show()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = "bookmark")
                    }
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
                        dashboardViewModel.updateStock(
                            id = updateBottomSheet.value.id,
                            nama = enableTextName,
                            stok = enableCount,
                            desc = enableTextDesc
                        )
                        updateBottomSheet.value = updateBottomSheet.value.copy(boolean = false)
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

    DisposableEffect(Unit) {
        onDispose { dashboardViewModel.leaveRealtimeChannel() }
    }

}


@Preview
@Composable
fun Pv() {

}

