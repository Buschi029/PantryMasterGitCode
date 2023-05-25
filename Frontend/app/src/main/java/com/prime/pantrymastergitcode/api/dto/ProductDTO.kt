package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO (
    @SerialName("product_name")
    val name: String,
    @SerialName("image_front_small_url")
    val image: String
    )

