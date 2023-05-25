package com.prime.pantrymastergitcode.api

import com.prime.pantrymastergitcode.api.dto.GetResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.lang.Exception

class OFFAPIServiceImplementation(
    private val client: HttpClient
) : OFFAPIService {
    override suspend fun getProduct(): GetResponse? {
        return try {
            client.get("url(HttpRoutes.product").body<GetResponse>()
        }catch (e: Exception){
            print(e)
            null
        }
    }
}

