package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import androidx.lifecycle.ViewModel
import com.prime.pantrymastergitcode.MainViewModel
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.toKotlinLocalDateTime

class ScannerViewModel(private val service: OFFAPIService, private val mainViewModel: MainViewModel) : ViewModel() {
    private val _product = MutableStateFlow(ProductDTO())
    val product = _product.asStateFlow()
    private val _pantryProduct = MutableStateFlow(PantryItemDTO())
    val pantryProduct = _pantryProduct.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _loaded = MutableStateFlow(false)
    val loaded = _loaded.asStateFlow()

    private val tag = "ScannerViewModel"

    // Funktion zum Setzen der Produkte
    fun setProduct(product: ProductDTO) {
        _product.value = product
    }

    // Funktion zum Setzen der Produkte
    fun setPantryProduct(pantryProduct: PantryItemDTO) {
        _pantryProduct.value = pantryProduct
    }

    // Funktion zur API-Abfrage anhand der Barcodes
    suspend fun getProductFromAPI(code: Long): Boolean = coroutineScope {
        var failed = false
        try {
            _loading.value = true
            _loaded.value = false
            _product.value = service.postProductDetails(code)!!
            Log.i("SVM", "method called with $code")
            _pantryProduct.value = _pantryProduct.value.copy(
                productCode = _product.value.productcode,
                productName = _product.value.name
            )
            _loading.value = false
            _loaded.value = true
        } catch (e: Exception) {
            _loading.value = false
            failed = true
            Log.e(tag, e.toString())
        }
        return@coroutineScope failed
    }

    // Funktion zum Hinzufügen des Produkts in die digitale Speisekammer
    suspend fun addProductToPantry(pantryProduct: PantryItemDTO): Boolean = coroutineScope {
        val today = java.time.LocalDateTime.now()
        val appendDate = today.toKotlinLocalDateTime()
        val userID = mainViewModel.getUserID()
        val pantryEntry = pantryProduct.copy(appendDate = appendDate, userID = userID)
        _pantryProduct.value = PantryItemDTO()
        _product.value = ProductDTO()
        _loaded.value = false
        val failed = async { service.postPantryEntry(pantryItemDTO = pantryEntry) }
        return@coroutineScope failed.await()
    }

    // Funktion zum Setzen der Loaded-Variable
    fun setLoaded(value: Boolean) {
        _loaded.value = value
    }
}
