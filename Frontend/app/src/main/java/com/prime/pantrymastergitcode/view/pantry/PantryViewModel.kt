package com.prime.pantrymastergitcode.view.pantry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.GetResponse
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.launch

class PantryViewModel(private val service: OFFAPIService) : ViewModel() {
    var errorMessage: String by mutableStateOf("")
    var loading: Boolean by mutableStateOf(false)
    var product: GetResponse by mutableStateOf(GetResponse("", ProductDTO("","")))

    fun getProduct(){
        viewModelScope.launch {
            try {
            product = service.getProduct()!!
            } catch (e: Exception) {
                loading = false
                errorMessage = e.message.toString()
                print(e)
            }

        }
    }
}
