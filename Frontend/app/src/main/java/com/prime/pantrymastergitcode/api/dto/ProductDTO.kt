package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    var carbohydrates: Int,
    var fat: Int,
    var kcal: Int,
    var nutriscore: String,
    var productcode: Long,
    var protein: Int,
    var sugar: Int
){
    constructor() : this(0,0,0,"",0,0,0)
}
