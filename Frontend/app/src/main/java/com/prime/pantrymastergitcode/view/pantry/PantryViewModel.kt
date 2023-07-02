package com.prime.pantrymastergitcode.view.pantry

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.MainViewModel
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel, welches die Logik der digitalen Speisekammer beinhaltet
class PantryViewModel(private val service: OFFAPIService, private val mainViewModel: MainViewModel): ViewModel() {

    // Initialisierung der Variablen
    val tag = "PantryViewModel"

    val items = mutableStateListOf<PantryItemDTO>()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _loadingPantry = MutableStateFlow(true)
    val loadingPantry = _loadingPantry.asStateFlow()

    private val _showProductDetails = MutableStateFlow(false)
    val showProductDetails = _showProductDetails.asStateFlow()

    private val _product = MutableStateFlow(ProductDTO())
    val product = _product.asStateFlow()

    val _sorted = MutableStateFlow(false)
    val sorted = _sorted.asStateFlow()

    // Funktion zum Abrufen der Produktdetails
    fun getProductDetails(code:Long, productName: String){
        _loading.value = false
        viewModelScope.launch {
            try {
                _product.value = service.postProductDetails(code)!!
                _loading.value = true
                _product.value = _product.value.copy(name = productName)
            } catch (e: Exception) {
                _loading.value = false
                Log.e("ScannerViewModel", e.toString())
            }
        }
    }

    // Funktion zum Setzen der Produktdetails
    fun setProductDetails(value: Boolean){
        _showProductDetails.value = value
    }

    // Funktion zur Abfrage der Produkte der digitalen Speisekammer
    fun getPantryItemsFromDatabase() {
        _loadingPantry.value = true
        viewModelScope.launch {
            try {
                items.clear()
                items.addAll(service.getPantryList("")!!)
                _loadingPantry.value = false
                _sorted.value = false
            } catch(e: Exception) {
                com.prime.pantrymastergitcode.util.Log.e("PantryViewModel", e.toString())
            }
        }
    }

    // Funktion zum Entfernen der Produkte der digitalen Speisekammer
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

    // Funktion zum Updaten der Produkte der digitalen Speisekammer
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

    // Funktion zum Abruf der PantryList
    fun getPantryList(): List<PantryItemDTO>{
        return items
    }

    // Funktion zum Sortieren der Liste nach Mindesthaltbarkeitsdatum
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

    // Funktion zum Überprüfen der Verfügbarkeit (String)
    fun checkAvailability(value:String): String{
        return if (value == "z"){
            "N/A"
        }else{
            value
        }
    }

    // Funktion zum Überprüfen der Verfügbarkeit (Int)
    fun checkAvailability(value: Int): String{
        return if (value == 9999){
            "N/A"
        }else{
            value.toString()
        }
    }
}
