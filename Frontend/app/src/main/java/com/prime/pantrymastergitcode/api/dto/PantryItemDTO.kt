package com.prime.pantrymastergitcode.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PantryItemDTO (

@SerialName("id")
val id: Long,

@SerialName("name")
val name: String,

@SerialName("productName")
val productName: String,

@SerialName("expirationDate")
val expirationDate: LocalDate,

@SerialName("appendDate")
val appendDate: LocalDate,

@SerialName("quantity")
val quantity: Int,

@SerialName("quantityUnit")
val quantityUnit: String
) {
    constructor() : this(0,"","",java.time.LocalDate.now().toKotlinLocalDate(), java.time.LocalDate.now().toKotlinLocalDate(), 0, "")
}
