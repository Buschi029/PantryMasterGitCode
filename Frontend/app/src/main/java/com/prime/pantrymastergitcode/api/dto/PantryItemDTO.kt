package com.prime.pantrymastergitcode.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.SerialName

class PantryItemDTO (

@SerialName("expirationDate")
val expirationDate: LocalDate,

@SerialName("id")
val id: Long,

@SerialName("name")
val name: String,

@SerialName("productName")
val productName: String,

@SerialName("quantity")
val quantity: Int,

@SerialName("quantityUnit")
val quantityUnit: String
) {

    constructor() : this(java.time.LocalDate.now().toKotlinLocalDate(),0,"","", 0, "")
}
