package com.faizdev.mykiosk.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.faizdev.mykiosk.data.kotpref.Kotpref
import com.faizdev.mykiosk.data.kotpref.Kotpref.isDarkMode

object GlobalState {
    var isDarkMode by mutableStateOf(Kotpref.isDarkMode)
}