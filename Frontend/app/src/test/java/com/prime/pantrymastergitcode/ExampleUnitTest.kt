package com.prime.pantrymastergitcode

import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import com.prime.pantrymastergitcode.view.scanner.ScannerViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var service: OFFAPIService

    @Before
    fun setup(){
        service = mockk()
    }
    @Test
    fun getProductDetails(){
        coEvery {
            service.postProductDetails(4000140703881)
        }returns ProductDTO(0,0,0,"Orangina original","","",0,0,0)
        val product = runBlocking {
            service.postProductDetails(4000140703881)
        }
        assertNotNull("Product should not be null", product)
        if (product != null) {
            assertEquals("Produkt wurde aus Backend geladen", "Orangina original", product.name)
        }
    }
}
