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

class PantryViewModel(private val service: OFFAPIService, ): ViewModel() {

    val tag = "PantryViewModel"

    val items = mutableStateListOf<PantryItemDTO>()

    private val unsortedItems = mutableStateListOf<PantryItemDTO>()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _loadingPantry = MutableStateFlow(false)
    val loadingPantry = _loadingPantry.asStateFlow()

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
                items.clear()
                items.addAll(service.getPantryList("")!!)
                _sorted.value = false
            } catch(e: Exception) {
                com.prime.pantrymastergitcode.util.Log.e("PantryViewModel", e.toString())
            }
        }
    }

    fun removeItemFromDatabase(pantryItem: PantryItemDTO) {
        viewModelScope.launch {
            try {
                items.remove(pantryItem)
                service.removeFromPantryList(pantryItemDTO = pantryItem)!!
            } catch(e: Exception) {
                com.prime.pantrymastergitcode.util.Log.e("PantryViewModel", e.toString())
            }
        }
    }

    fun updatePantryItem(pantryItem: PantryItemDTO, index: Int){
        viewModelScope.launch {
            try{
                items[index] = pantryItem
                service.updatePantryItem(pantryItem)!!
            }catch (e:Exception){
                Log.e(tag, e.toString())
            }
        }
    }
    fun getPantryList(): List<PantryItemDTO>{
        return items
    }
    fun sortList(){
        if(!sorted.value) {
            items.sortBy {
                it.expirationDate
            }
            _sorted.value = true
        }else if(sorted.value){
            items.sortBy {
                it.appendDate
            }
            _sorted.value = false
        }
    }
}
