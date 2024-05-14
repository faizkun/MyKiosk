package com.faizdev.mykiosk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark (
    @PrimaryKey(false)
    val id : Int,
    val name : String,
    val stock : String,
    val description : String,
)