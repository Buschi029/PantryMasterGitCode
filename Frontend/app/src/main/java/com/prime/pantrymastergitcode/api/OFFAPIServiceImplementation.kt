package com.prime.pantrymastergitcode.api

import android.content.ContentValues.TAG
import android.util.Log
import com.prime.pantrymastergitcode.api.dto.ProductBarcodeDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.JsonConvertException


class OFFAPIServiceImplementation(
    private val client: HttpClient
) : OFFAPIService {
    override suspend fun postProductDetails(code: Long): ProductDTO? {
        val response: HttpResponse
        val body : ProductDTO
        Log.i("off","starrt")
        return try {
            response = client.post(HttpRoutes.product) {
                contentType(ContentType.Application.Json)
                setBody(ProductBarcodeDTO(code))
            }
            Log.e("Off", response.status.toString())
            body = response.body()
            body
        }catch (e: Exception){
            Log.e(TAG,e.toString())
            null
        }
    }
}

