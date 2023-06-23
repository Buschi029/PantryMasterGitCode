package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.PantryProductDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

class ScannerViewModel(private val service: OFFAPIService): ViewModel() {
    private val _littleInput = MutableStateFlow(false)
    val littleInput = _littleInput.asStateFlow()
    val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    private val _product = MutableStateFlow(ProductDTO())
    val product = _product.asStateFlow()
    private val _pantryProduct = MutableStateFlow(PantryProductDTO())
    val pantryProduct = _pantryProduct.asStateFlow()




    fun setProduct(product: ProductDTO){
        _product.value = product
    }

    fun setPantryProduct(pantryProduct: PantryProductDTO){
        _pantryProduct.value = pantryProduct
    }
    fun getProductFromAPI(code: Long) {
        viewModelScope.launch {
            try {
                _product.value = service.postProductDetails(code)!!
                Log.i("SVM", "method called with $code")
            } catch (e: Exception) {
//                loading = false
//                errorMessage = e.message.toString()
                Log.e("ScannerViewModel", e.toString())
            }
        }
    }
    fun postProductToPantry(pantryProduct: PantryProductDTO) {
        val today = java.time.LocalDate.now()
        val appendDate = LocalDate(today.year,today.month,today.dayOfMonth)
        val defaultExpirationDate = java.time.LocalDate.now().toKotlinLocalDate()
        if(pantryProduct.productName == ""){
            _littleInput.value = true
            return
        }else if (pantryProduct.expirationDate == defaultExpirationDate){
            _littleInput.value = true
            return
        }else if (pantryProduct.quantity == 0){
            _littleInput.value = true
            return
        }else if (pantryProduct.quantityUnit == ""){
            _littleInput.value = true
            return
        }
        _pantryProduct.value = pantryProduct.copy(appendDate = appendDate)
        viewModelScope.launch {
            service.postPantryEntry(pantryProductDTO = pantryProduct)
        }
        _pantryProduct.value = PantryProductDTO()
    }
}
