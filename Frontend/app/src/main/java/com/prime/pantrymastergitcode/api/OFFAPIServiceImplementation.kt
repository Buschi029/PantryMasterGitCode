package com.prime.pantrymastergitcode.api

import android.util.Log
import com.prime.pantrymastergitcode.api.dto.PantryProductDTO
import com.prime.pantrymastergitcode.api.dto.ProductBarcodeDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import com.prime.pantrymastergitcode.api.dto.ShoppingListDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType

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
            response = client.get("http://192.168.0.245:8081/shoppingList") {
                contentType(ContentType.Application.Json)

            }
            shoppingList = response.body()
            shoppingList
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            null
        }
    }
}
