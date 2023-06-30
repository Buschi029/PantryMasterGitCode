package com.prime.pantrymastergitcode.api.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PantryListDTO (
    @SerialName("")
    val pantryList: List<PantryProductDTO>?= emptyList()
)
{
    constructor() : this(listOf())
}
