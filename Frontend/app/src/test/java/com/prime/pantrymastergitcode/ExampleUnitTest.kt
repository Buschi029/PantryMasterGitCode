package com.prime.pantrymastergitcode

import com.prime.pantrymastergitcode.api.OFFAPIService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    private val service = OFFAPIService.create()

    @Test
    fun getProductDetails() {
        runBlocking {
            val product = service.postProductDetails(4000140703881)
            assertNotNull("Product should not be null", product)
            if (product != null) {
                assertEquals("Produkt wurde aus Backend geladen", "Orangina original", product.name)
            }
        }
    }
}
