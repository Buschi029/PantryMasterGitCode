package com.prime.pantrymastergitcode.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PantryItemDTO (
val productCode: Long,
val userID: String,
val productName: String,
val expirationDate: LocalDate,
val appendDate: LocalDateTime,
var quantity: Int,
val quantityUnit: String
) {
    constructor() : this(0,"","",java.time.LocalDate.now().toKotlinLocalDate(), java.time.LocalDateTime.now().toKotlinLocalDateTime(), 0, "")
}
