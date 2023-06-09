package com.prime.pantrymastergitcode.api

import android.util.Log
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.ProductBarcodeDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

// Klasse, welche die HTTP-Request beinhaltet
class OFFAPIServiceImplementation(
    private val client: HttpClient
) : OFFAPIService {
    private val tag = "APIService"

    // Funktion zum Hinzufügen von Produktdetails
    override suspend fun postProductDetails(code: Long): ProductDTO? {
        val response: HttpResponse
        val body: ProductDTO
        return try {
            response = client.post(HttpRoutes.product) {
                contentType(ContentType.Application.Json)
                setBody(ProductBarcodeDTO(code))
            }
            Log.i(tag, response.status.toString())
            body = response.body()
            body
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

    // Funktion zum Hinzufügen von Produkteinträgen
    override suspend fun postPantryEntry(pantryItemDTO: PantryItemDTO): Boolean {
        val response: HttpResponse
        return try {
            response = client.put(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                setBody(pantryItemDTO)
            }
            Log.i(tag, response.status.toString())
            false
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            true
        }
    }


    // Funktion zur Abfrage von ShoppingListItems
    override suspend fun getShoppingList(userID: String): List<ShoppingItemDTO>? {
        val response: HttpResponse
        val shoppingList: List<ShoppingItemDTO>
        return try {
            response = client.get(HttpRoutes.shoppingList) {
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("userID", userID)
                }
            }
            shoppingList = response.body()
            shoppingList
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

    // Funktion zum Entfernen von ShoppingList-Einträgen
    override suspend fun removeFromShoppingList(productName: String, userID: String): List<ShoppingItemDTO>? {
        val response: HttpResponse

        return try {
            val item = mapOf("userID" to userID, "productName" to productName)
            response = client.delete(HttpRoutes.shoppingList) {
                contentType(ContentType.Application.Json)
                setBody(item)
            }

            Log.i(tag, response.status.toString())

            if (response.status.isSuccess()) {
                val updatedListResponse: HttpResponse = client.get(HttpRoutes.shoppingList) {
                    contentType(ContentType.Application.Json)
                }
                val updatedList: List<ShoppingItemDTO> = updatedListResponse.body()
                updatedList
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

    // Funktion zum Hinzufügen von ShoppingItems
    override suspend fun addToShoppingList(productName: String, quantity: Int, quantityUnit: String, userID: String): List<ShoppingItemDTO>? {
        val response: HttpResponse
        return try {
            val item = ShoppingItemDTO(productName, quantity, quantityUnit, userID)
            response = client.put(HttpRoutes.shoppingList) {
                contentType(ContentType.Application.Json)
                setBody(item)
            }

            Log.i(tag, response.status.toString())

            if (response.status.isSuccess()) {
                val updatedListResponse: HttpResponse = client.get(HttpRoutes.shoppingList) {
                    contentType(ContentType.Application.Json)
                }
                val updatedList: List<ShoppingItemDTO> = updatedListResponse.body()
                updatedList
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

    // Funktion zum Abrufen der PantryList
    override suspend fun getPantryList(userID: String): MutableList<PantryItemDTO>? {
        val response: HttpResponse
        val pantryList: MutableList<PantryItemDTO>
        return try {
            response = client.get(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("userID", userID)
                }
            }
            pantryList = response.body()
            pantryList
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

    // Funktion zum Entfernen von Pantry-Einträgen
    override suspend fun removeFromPantryList(pantryItemDTO: PantryItemDTO): MutableList<PantryItemDTO>? {
        val response: HttpResponse
        val updatedList: MutableList<PantryItemDTO>

        return try {
            response = client.delete(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                setBody(pantryItemDTO)
            }
            Log.i(tag, response.status.toString())
            updatedList = response.body()
            updatedList
            } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

    // Funktion zun Updaten von PantryItems
    override suspend fun updatePantryItem(pantryItemDTO: PantryItemDTO): MutableList<PantryItemDTO>? {
        val response: HttpResponse
        val updatedList: MutableList<PantryItemDTO>

        return try {
            response = client.patch(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                setBody(pantryItemDTO)
            }
            Log.i(tag, response.status.toString())
            updatedList = response.body()
            updatedList
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }
}
