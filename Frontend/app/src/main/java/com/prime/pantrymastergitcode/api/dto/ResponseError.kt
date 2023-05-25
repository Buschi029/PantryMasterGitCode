package com.prime.pantrymastergitcode.api.dto

@kotlinx.serialization.Serializable
data class ResponseError (
    val code: String,
    val message: String
        )
