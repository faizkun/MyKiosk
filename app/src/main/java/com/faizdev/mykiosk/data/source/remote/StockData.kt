package com.faizdev.mykiosk.data.source.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StokData(
    @SerialName("name") val name: String,
    @SerialName("stock") val stock: Int,
    @SerialName("description") val description: String,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("id") val id: Int? = null,
)
