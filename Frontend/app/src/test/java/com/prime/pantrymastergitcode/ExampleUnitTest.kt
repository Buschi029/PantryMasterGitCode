package com.prime.pantrymastergitcode

import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.view.scanner.ScannerViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val service = OFFAPIService.create()

    @Test
    fun getProductDetails() = runTest {
        val product = service.postProductDetails(4000140703881)
        assertNotNull("Product should not be null", product)
        if (product != null) {
            assertEquals("Produkt wurde aus Backend geladen", "Orangina original", product.name)
        }
    }
}


}
