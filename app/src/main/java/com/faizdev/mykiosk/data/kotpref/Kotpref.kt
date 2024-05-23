package com.faizdev.mykiosk.data.kotpref

import com.chibatching.kotpref.KotprefModel

object Kotpref : KotprefModel() {
    var id by nullableStringPref("")
    var username by nullableStringPref("")
    var isLogin by booleanPref(false)
    var isDarkMode by booleanPref(true)
}