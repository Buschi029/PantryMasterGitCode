package com.prime.pantrymastergitcode.api.dto

import kotlinx.serialization.Serializable

// Festlegung der Eigenschaften eines ProductBarcodeDTOs
@Serializable
data class ProductBarcodeDTO(
    val barcode: Long
)
