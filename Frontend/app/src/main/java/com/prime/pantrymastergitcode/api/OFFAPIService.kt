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

// Festlegung der Interfaces
interface OFFAPIService {

    // Initialisierung der Funktionen
    suspend fun postProductDetails(code: Long): ProductDTO?
    suspend fun postPantryEntry(pantryItemDTO: PantryItemDTO): Boolean

    suspend fun getShoppingList(userID: String): List<ShoppingItemDTO>?

    suspend fun addToShoppingList(productName: String, quantity: Int, quantityUnit: String, userID: String): List<ShoppingItemDTO>?

    suspend fun removeFromShoppingList(productName: String, userID: String): List<ShoppingItemDTO>?

    suspend fun getPantryList(userID: String): MutableList<PantryItemDTO>?

    suspend fun removeFromPantryList(pantryItemDTO: PantryItemDTO): MutableList<PantryItemDTO>?

    suspend fun updatePantryItem(pantryItemDTO: PantryItemDTO): MutableList<PantryItemDTO>?

    // Erstellung des OFFAPIServices
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
