package com.faizdev.mykiosk.screen.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.faizdev.mykiosk.data.kotpref.Kotpref
import com.faizdev.mykiosk.screen.destinations.DashboardScreenDestination
import com.faizdev.mykiosk.screen.destinations.LoginScreenDestination
import com.faizdev.mykiosk.screen.destinations.SettingScreenDestination
import com.faizdev.mykiosk.utils.GlobalState
import com.faizdev.ui.theme.poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    var checked by remember {
        mutableStateOf(Kotpref.isDarkMode)
    }

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

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

                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                GlobalState.isDarkMode = it
                                Kotpref.isDarkMode = it
                            },
                            modifier = Modifier.padding(end = 16.dp)

                        )
                    }


                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(63.dp)
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp)
                            .clickable {
                                showAlertDialog = true

                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface)


                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {


                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = "Log Out",
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface


                            )
                        }
                    }
                }

                if (showAlertDialog) {
                    AlertDialog(
                        title = { Text(
                            text = "Keluar dari akun anda?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold

                        ) },
                        onDismissRequest = {showAlertDialog = false},
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    settingViewModel.onLogout()
                                   Kotpref.isLogin = false
                                    navigator.navigate(LoginScreenDestination){
                                        popUpTo(SettingScreenDestination.route){
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                    Kotpref.clear()
                                }) {
                                Text(text = "Keluar")
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

        }


    }
}



