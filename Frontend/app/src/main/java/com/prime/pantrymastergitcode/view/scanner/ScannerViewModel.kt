package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.internal.FallbackServiceBroker
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.PantryProductDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

class ScannerViewModel(private val service: OFFAPIService) : ViewModel() {
    private val _product = MutableStateFlow(ProductDTO())
    val product = _product.asStateFlow()
    private val _pantryProduct = MutableStateFlow(PantryProductDTO())
    val pantryProduct = _pantryProduct.asStateFlow()

    val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    val _loaded = MutableStateFlow(false)
    val loaded = _loaded.asStateFlow()


    fun setProduct(product: ProductDTO) {
        _product.value = product
    }

    fun setPantryProduct(pantryProduct: PantryProductDTO) {
        _pantryProduct.value = pantryProduct
    }

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
            Log.e("ScannerViewModel", e.toString())
        }
        return@coroutineScope failed
    }

    fun postProductToPantry(pantryProduct: PantryProductDTO) {
        val today = java.time.LocalDate.now()
        val appendDate = LocalDate(today.year, today.month, today.dayOfMonth)
        _pantryProduct.value = pantryProduct.copy(appendDate = appendDate)
        viewModelScope.launch {
            service.postPantryEntry(pantryProductDTO = pantryProduct)
        }
        _pantryProduct.value = PantryProductDTO()
        _product.value = ProductDTO()
        _loaded.value = false
    }

    fun setLoaded(value: Boolean) {
        _loaded.value = value
    }
}
