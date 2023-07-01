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
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

class OFFAPIServiceImplementation(
    private val client: HttpClient
) : OFFAPIService {
    private val tag = "APIService"

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

    override suspend fun postPantryEntry(pantryItemDTO: PantryItemDTO) {
        val response: HttpResponse
        try {
            response = client.put(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                setBody(pantryItemDTO)
            }
            Log.i(tag, response.status.toString())
        } catch (e: Exception) {
            Log.e(tag, e.toString())
        }
    }

    override suspend fun httpRequestShopping(shoppingItemDTO: ShoppingItemDTO) {
        val response: HttpResponse

    }

    override suspend fun getShoppingList(userID: String): List<ShoppingItemDTO>? {
        val response: HttpResponse
        val shoppingList: List<ShoppingItemDTO>
        return try {
            response = client.get(HttpRoutes.shoppingList) {
                contentType(ContentType.Application.Json)

            }
            shoppingList = response.body()
            shoppingList
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

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


    override suspend fun getPantryList(name: String): List<PantryItemDTO>? {
        val response: HttpResponse
        val pantryList: List<PantryItemDTO>
        return try {
            response = client.get(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
            }
            pantryList = response.body()
            pantryList
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

    override suspend fun removeFromPantryList(pantryItemDTO: PantryItemDTO): List<PantryItemDTO>? {
        val response: HttpResponse
        val updatedList: List<PantryItemDTO>

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

}
