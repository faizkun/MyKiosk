package com.faizdev.mykiosk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark (
    val name : String,
    val stock : Int,
    val description : String,
    @PrimaryKey(false)
    val id : Int,
)