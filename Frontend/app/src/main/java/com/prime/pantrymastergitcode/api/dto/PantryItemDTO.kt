package com.prime.pantrymastergitcode.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PantryItemDTO (

    @SerialName("productCode")
val productCode: Long,

    @SerialName("userID")
val userID: String,

    @SerialName("productName")
val productName: String,

    @SerialName("expirationDate")
val expirationDate: LocalDate,

    @SerialName("appendDate")
val appendDate: LocalDateTime,

    @SerialName("quantity")
var quantity: Int,

    @SerialName("quantityUnit")
val quantityUnit: String
) {
    constructor() : this(0,"","",java.time.LocalDate.now().toKotlinLocalDate(), java.time.LocalDateTime.now().toKotlinLocalDateTime(), 0, "")
}
