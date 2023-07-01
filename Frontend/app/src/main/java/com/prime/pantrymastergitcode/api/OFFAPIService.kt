package com.prime.pantrymastergitcode.api

import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json

interface OFFAPIService {
    suspend fun postProductDetails(code: Long): ProductDTO?
    suspend fun postPantryEntry(pantryItemDTO: PantryItemDTO)
    // Shopping List
    suspend fun httpRequestShopping(shoppingItemDTO: ShoppingItemDTO)

    suspend fun getShoppingList(userID: String): List<ShoppingItemDTO>?

    suspend fun addToShoppingList(productName: String, quantity: Int, quantityUnit: String, userID: String): List<ShoppingItemDTO>?

    suspend fun removeFromShoppingList(productName: String, userID: String): List<ShoppingItemDTO>?

    suspend fun getPantryList(name: String): List<PantryItemDTO>?

    suspend fun removeFromPantryList(pantryItemDTO: PantryItemDTO): List<PantryItemDTO>?

    companion object {
        fun create(): OFFAPIService {
            return OFFAPIServiceImplementation(
                client = HttpClient(CIO) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        json(
                            Json {
                                ignoreUnknownKeys = true
                                isLenient = true
                            }
                        )
                    }
                }
            )
        }
    }
}
