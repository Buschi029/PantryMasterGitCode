package com.prime.pantrymastergitcode.api

import com.prime.pantrymastergitcode.api.dto.ProductDTO
import io.ktor.client.engine.cio.CIO
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json

interface OFFAPIService {

    suspend fun postProductDetails(code: Long): ProductDTO?

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
