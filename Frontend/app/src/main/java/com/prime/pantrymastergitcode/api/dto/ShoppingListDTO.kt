package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListDTO(
    @SerialName("")
    val shoppingList: List<ShoppingItemDTO>?= emptyList()


){
    constructor() : this(listOf())
}
