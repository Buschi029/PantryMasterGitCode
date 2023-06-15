package com.prime.pantrymastergitcode.api

import android.content.ContentValues.TAG
import android.util.Log
import com.prime.pantrymastergitcode.api.dto.ProductBarcodeDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import io.ktor.client.call.body
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class OFFAPIServiceImplementation(
    private val client: HttpClient
) : OFFAPIService {
    override suspend fun postProductDetails(code: Long): ProductDTO? {
        val response: HttpResponse
        val body: ProductDTO
        return try {
            response = client.post(HttpRoutes.product) {
                contentType(ContentType.Application.Json)
                setBody(ProductBarcodeDTO(code))
            }
            Log.i("Off", response.status.toString())
            body = response.body()
            body
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            null
        }
    }
}
