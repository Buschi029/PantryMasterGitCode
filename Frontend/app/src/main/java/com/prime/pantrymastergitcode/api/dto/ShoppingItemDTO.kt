package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
    var isChecked: Boolean = false
    )
    {
        constructor() : this("",0, "", "")
    }


