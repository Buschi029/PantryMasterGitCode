package com.prime.pantrymastergitcode.view.shoppingList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.MainViewModel
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import com.prime.pantrymastergitcode.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod


class ShoppingListViewModel(private val service: OFFAPIService, private val mainViewModel: MainViewModel): ViewModel() {
    private val _items = mutableStateListOf<ShoppingItem>()
    var items: List<ShoppingItemDTO> by mutableStateOf(mutableListOf())

    private val _newItem = mutableStateOf("")
    val newItem: String get() = _newItem.value

    private val _newQuantity = mutableStateOf("")
    val newQuantity: String get() = _newQuantity.value

    private val _newQuantityType = mutableStateOf("")
    val newQuantityType: String get() = _newQuantityType.value

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    fun getItemsFromDatabase() {
        _loading.value = true
        viewModelScope.launch {
            try {
                items = service.getShoppingList("")!!
                _loading.value = false
            } catch (e: Exception) {
                Log.e("ShoppingListViewModel", e.toString())
            }
        }
    }

    fun addItemsToDatabase(productName: String, quantity: Int, quantityUnit: String) {
        viewModelScope.launch {
            try {
                items = service.addToShoppingList(productName, quantity, quantityUnit, "")!!
            } catch (e: Exception) {
                Log.e("ShoppingListViewModel", e.toString())
            }
        }
    }

    fun removeItemFromDatabase(productName: String, quantity: Int, quantityUnit: String) {
        viewModelScope.launch {
            try {
                items = service.removeFromShoppingList(productName, "")!!
            } catch (e: Exception) {
                Log.e("ShoppingListViewModel", e.toString())
            }
        }
    }
}
