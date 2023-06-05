package com.prime.pantrymastergitcode.api

import android.content.ContentValues.TAG
import android.util.Log
import com.prime.pantrymastergitcode.api.dto.GetResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException


class OFFAPIServiceImplementation(
    private val client: HttpClient
) : OFFAPIService {
    override suspend fun getProduct(code : String): GetResponse? {
        var response: GetResponse
         try {
             response = client.get(HttpRoutes.product + code).body()
             return response
        }catch (e: JsonConvertException){
            Log.e(TAG,e.toString())
            return null
        }
    }
}

