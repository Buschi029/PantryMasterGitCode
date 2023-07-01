package com.prime.pantrymastergitcode.view.pantry

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PantryViewModel(private val service: OFFAPIService, ): ViewModel() {

    val tag = "PantryViewModel"
    private val _items = mutableStateListOf<PantryProduct>()
    var items: List<PantryItemDTO> by mutableStateOf(mutableListOf())

    var unsortedItems: List<PantryItemDTO> by mutableStateOf(mutableListOf())

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _showProductDetails = MutableStateFlow(false)
    val showProductDetails = _showProductDetails.asStateFlow()

    private val _product = MutableStateFlow(ProductDTO())
    val product = _product.asStateFlow()

    val _sorted = MutableStateFlow(false)
    val sorted = _sorted.asStateFlow()

    fun getProductDetails(code:Long){
        _loading.value = false
        viewModelScope.launch {
            try {
                _product.value = service.postProductDetails(code)!!
                _loading.value = true
            } catch (e: Exception) {
                _loading.value = false
                Log.e("ScannerViewModel", e.toString())
            }
        }
    }
    fun setProductDetails(value: Boolean){
        _showProductDetails.value = value
    }

    fun getPantryItemsFromDatabase() {
        viewModelScope.launch {
            try {
                items = service.getPantryList("")!!
            } catch(e: Exception) {
                com.prime.pantrymastergitcode.util.Log.e("PantryViewModel", e.toString())
            }
        }
    }

    fun removeItemFromDatabase(pantryItem: PantryItemDTO) {
        viewModelScope.launch {
            try {
                items = service.removeFromPantryList(pantryItemDTO = pantryItem)!!
            } catch(e: Exception) {
                com.prime.pantrymastergitcode.util.Log.e("PantryViewModel", e.toString())
            }
        }
    }

    fun setSorted(value: Boolean){
        _sorted.value = value
    }

    fun updatePantryItem(pantryItem: PantryItemDTO){
        items = runBlocking {
            service.updatePantryItem(pantryItem)!!
        }
    }
}
