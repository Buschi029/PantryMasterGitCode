package com.prime.pantrymastergitcode.view.scanner

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.launch

class ScannerViewModel(private val service: OFFAPIService) : ViewModel() {
    var errorMessage: String by mutableStateOf("")
    var loading: Boolean by mutableStateOf(false)
    var product: ProductDTO by mutableStateOf(
        ProductDTO(0,0,0,"",0,0,0)
    )

    fun getProduct(code : Long){
        Log.i("Test","Methodenaufruf gestartet 1")
        viewModelScope.launch {
            try {
                Log.i("Test","Methodenaufruf gestartet 2")
                product = service.postProductDetails(code)!!

            } catch (e: Exception) {
                loading = false
                errorMessage = e.message.toString()
                Log.e("ScannerViewModel",e.toString())
            }
        }
    }
}
