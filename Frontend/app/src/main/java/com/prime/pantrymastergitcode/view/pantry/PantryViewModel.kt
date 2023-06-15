package com.prime.pantrymastergitcode.view.pantry

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.launch

class PantryViewModel(private val service: OFFAPIService, ): ViewModel() {
    var product: ProductDTO by mutableStateOf(ProductDTO())
    var loading : Boolean by mutableStateOf(false)
    var showProductDetails: Boolean by mutableStateOf(false)


    fun getProductDetails(code:Long){
        loading = false
        viewModelScope.launch {
            try {
                product = service.postProductDetails(code)!!
                loading = true
            } catch (e: Exception) {
                loading = false
                Log.e("ScannerViewModel", e.toString())
            }
        }
    }
}
