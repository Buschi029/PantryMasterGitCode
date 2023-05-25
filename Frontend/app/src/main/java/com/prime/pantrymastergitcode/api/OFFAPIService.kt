package com.prime.pantrymastergitcode.api

import com.prime.pantrymastergitcode.api.dto.GetResponse

interface OFFAPIService {
    suspend fun getProduct(): GetResponse?
}
