package com.prime.pantrymastergitcode.api

import android.util.Log
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.PantryListDTO
import com.prime.pantrymastergitcode.api.dto.PantryProductDTO
import com.prime.pantrymastergitcode.api.dto.ProductBarcodeDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import com.prime.pantrymastergitcode.api.dto.ShoppingListDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime

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

    override suspend fun postPantryEntry(pantryProductDTO: PantryProductDTO) {
        val response: HttpResponse
        try {
            response = client.post(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                setBody(pantryProductDTO)
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
        val shoppingListDTO: ShoppingListDTO
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


    // Pantry List
    override suspend fun addToPantryList(productCode: Long, productName: String, userID: String, expirationDate: LocalDate , quantity: Int, quantityUnit: String): List<PantryItemDTO>? {
        val response: HttpResponse

        return try {
            val item = PantryItemDTO(productCode,productName,userID,expirationDate,java.time.LocalDate.now().toKotlinLocalDate(),quantity,quantityUnit)
            response = client.post(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                setBody(item)
            }

            Log.i(tag, response.status.toString())

            if (response.status.isSuccess()) {
                val updatedListResponse: HttpResponse = client.get(HttpRoutes.inventory) {
                    contentType(ContentType.Application.Json)
                }
                val updatedList: List<PantryItemDTO> = updatedListResponse.body()
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
        val pantryListDTO: PantryListDTO
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

    override suspend fun removeFromPantryList(barcode: Long, userID: String): List<PantryItemDTO>? {
        val response: HttpResponse

        return try {
            val item = mapOf("barcode" to barcode, "userID" to userID)
            response = client.delete(HttpRoutes.inventory) {
                contentType(ContentType.Application.Json)
                setBody(item)
            }

            Log.i(tag, response.status.toString())

            if (response.status.isSuccess()) {
                val updatedListResponse: HttpResponse = client.get(HttpRoutes.inventory) {
                    contentType(ContentType.Application.Json)
                }
                val updatedList: List<PantryItemDTO> = updatedListResponse.body()
                updatedList
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }

}
