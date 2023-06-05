package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetResponse (
    val code: String,
    @SerialName("product")
    val product: ProductDTO
)

