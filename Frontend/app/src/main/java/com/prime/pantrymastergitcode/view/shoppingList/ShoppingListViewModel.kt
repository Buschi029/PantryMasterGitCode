package com.prime.pantrymastergitcode.view.shoppingList

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class ShoppingListViewModel: ViewModel() {

    /*
    data class ShoppingItem(val name: String, var quantity: String,
                            var quantityType: String, var isChecked: Boolean = false)

    private val _items = MutableStateFlow(false)
    val items = _items.asStateFlow()

    private val _newItem = MutableStateFlow(false)
    val newItem = _newItem.asStateFlow()

    private val _newQuantity = MutableStateFlow(false)
    val newQuantity = _newQuantity.asStateFlow()

    private val _newQuantityType = MutableStateFlow(false)
    val newQuantityType = _newQuantityType.asStateFlow()
    */

    private val _items = mutableStateListOf<ShoppingItem>()
    val items: List<ShoppingItem> get() = _items

    private val _newItem = mutableStateOf("")
    val newItem: String get() = _newItem.value

    private val _newQuantity = mutableStateOf("")
    val newQuantity: String get() = _newQuantity.value

    private val _newQuantityType = mutableStateOf("")
    val newQuantityType: String get() = _newQuantityType.value


    fun addItem() {
        val newItem = newItem
        val newQuantity = newQuantity
        val newQuantityType = newQuantityType

        _items.add(ShoppingItem(newItem, newQuantity, newQuantityType))
        _newItem.value = ""
        _newQuantity.value = ""
        _newQuantityType.value = ""
    }

    fun removeItem(item: ShoppingItem) {
        _items.remove(item)
    }
}

    /*
    fun fetchShoppingList() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = httpClient.get<List<ShoppingItem>>("http://localhost:8081/shoppingList")
                withContext(Dispatchers.Main) {
                    _items.clear()
                    _items.addAll(response)
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}

     */
