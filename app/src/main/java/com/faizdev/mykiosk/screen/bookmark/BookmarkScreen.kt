package com.faizdev.mykiosk.screen.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.faizdev.mykiosk.data.local.entity.Bookmark
import com.faizdev.mykiosk.items.BookmarkItem
import com.faizdev.mykiosk.items.StockItem
import com.faizdev.mykiosk.state.BottomSheetState
import com.faizdev.ui.theme.poppins

import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BookmarkScreen(
    navigator: DestinationsNavigator,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {

    val getAllBookmark by bookmarkViewModel.bookmarkState.collectAsStateWithLifecycle()


    Scaffold(

        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Bookmark",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.navigateUp()
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

            getAllBookmark.DisplayResult(
                onLoading = { LinearProgressIndicator() },
                onSuccess = {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        content = {
                            items(it) {
                                BookmarkItem(
                                    name = it.name,
                                    stockCount = it.stock,
                                    description = it.description,
                                    onClick = { nama, stok, desc ->

                                        bookmarkViewModel.deleteBookmark(
                                            Bookmark(
                                                name = it.name,
                                                stock = it.stock,
                                                description = it.description,
                                                id = it.id
                                            )
                                        )
                                    }
                                ) {

                                }
                            }
                        }
                    )
                },
                onError = {}
            )

        }




    }
}
