package com.faizdev.mykiosk.utils

import android.util.Patterns

fun String.emailChecked() = !Patterns.EMAIL_ADDRESS.matcher(this).matches()