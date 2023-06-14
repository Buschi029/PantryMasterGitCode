package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScannerViewModel(): ViewModel() {
//    var errorMessage: String by mutableStateOf("")
//    var loading: Boolean by mutableStateOf(false)
    private val _product = MutableStateFlow(ProductDTO())
    val product = _product.asStateFlow()
    private val service = OFFAPIService.create()


    fun getProduct(code: Long) {
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
}
