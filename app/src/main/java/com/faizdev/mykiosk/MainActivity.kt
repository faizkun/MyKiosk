package com.faizdev.mykiosk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.faizdev.compose.MyKioskTheme
import com.faizdev.mykiosk.data.kotpref.Kotpref
import com.faizdev.mykiosk.screen.NavGraphs
import com.faizdev.mykiosk.screen.destinations.DashboardScreenDestination
import com.faizdev.mykiosk.screen.destinations.LoginScreenDestination
import com.faizdev.mykiosk.utils.GlobalState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MyKioskTheme (
                darkTheme = GlobalState.isDarkMode
                ) {
                   DestinationsNavHost(

                       navGraph = NavGraphs.root,
                       startRoute = if(Kotpref.username.isNullOrEmpty()) LoginScreenDestination else DashboardScreenDestination
                   )
                }
            }
        }
    }

