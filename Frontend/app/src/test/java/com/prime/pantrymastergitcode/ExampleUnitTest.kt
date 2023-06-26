package com.prime.pantrymastergitcode

import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.view.scanner.ScannerViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

import org.junit.Assert.*

class ExampleUnitTest {
    private lateinit var service: OFFAPIService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        service = Mockito.mock(OFFAPIService::class.java)
    }

    @Test
    fun getProductDetails() = runBlockingTest {
        // Assume that the product name would be "Orangina original"
        `when`(service.postProductDetails(4000140703881)).thenReturn(Product(name = "Orangina original"))

        val product = service.postProductDetails(4000140703881)
        assertEquals("Produkt wurde aus Backend geladen", product!!.name, "Orangina original")
    }
}

