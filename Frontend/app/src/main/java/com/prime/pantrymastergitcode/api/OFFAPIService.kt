package com.prime.pantrymastergitcode.api

import com.prime.pantrymastergitcode.api.dto.GetResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json




interface OFFAPIService {
    suspend fun getProduct(): GetResponse?

    companion object {
        fun create(): OFFAPIService {
            return OFFAPIServiceImplementation(
                client = HttpClient(CIO) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        json(Json{
                            ignoreUnknownKeys = true
                            isLenient = true
                        })
                    }
                }
            )
        }
    }
}
