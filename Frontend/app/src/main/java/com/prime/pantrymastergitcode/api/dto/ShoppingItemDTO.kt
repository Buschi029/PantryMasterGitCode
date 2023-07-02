package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ShoppingItemDTO (
    val productName: String,
    val quantity: Int,
    val quantityUnit: String,
    val userID: String)
    {
        constructor() : this("",0, "", "")
    }


