package com.prime.pantrymastergitcode.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PantryItemDTO (

@SerialName("id")
val id: Long,

@SerialName("name")
val name: String,

@SerialName("productName")
val productName: String,

@SerialName("expirationDate")
val expirationDate: String,

@SerialName("quantity")
val quantity: Int,

@SerialName("quantityUnit")
val quantityUnit: String
) {

    constructor() : this(0,"","",java.time.LocalDate.now().toKotlinLocalDate().toString(), 0, "")
}
