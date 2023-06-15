package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val carbohydrates: Int,
    val fat: Int,
    val kcal: Int,
    val name: String,
    val nutriscore: String,
    val pictureLink: String,
    val productcode: Long,
    val protein: Int,
    val sugar: Int
){
    constructor() : this(0,0,0,"", "", "",0,0,0)
}
