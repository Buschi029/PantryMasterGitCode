package com.prime.pantrymastergitcode.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class PantryProductDTO(
    val productCode: Long,
    val productName: String,
    val userID: String,
    val expirationDate: LocalDate,
    val appendDate: LocalDate,
    val quantity: Int,
    val quantityUnit: String
){
    constructor() : this(0,"","", java.time.LocalDate.now().toKotlinLocalDate(), java.time.LocalDate.now().toKotlinLocalDate(), 0,"")
}
