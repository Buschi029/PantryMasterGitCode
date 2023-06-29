package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingItemDTO (
    @SerialName("productName")
    val productName: String,

    @SerialName("quantity")
    val quantity: Int,

    @SerialName("quantityUnit")
    val quantityUnit: String,

    @SerialName("userID")
    val userID: String,

    @Transient
    val isChecked: Boolean
    )
    {
        constructor() : this("",0, "", "",false )
    }


