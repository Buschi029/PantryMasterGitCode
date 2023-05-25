package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetResponse (
    @SerialName("code")
    val code: String,
    @SerialName("product.product_name")
    val name: String,
    @SerialName("product.image_front_small_url")
    val image: String

)
