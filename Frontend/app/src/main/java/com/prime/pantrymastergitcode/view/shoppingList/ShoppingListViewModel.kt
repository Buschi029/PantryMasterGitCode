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
        //Test

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

package com.prime.pantrymastergitcode.view.shoppingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


class ShoppingListViewModel : ViewModel() {
    data class ShoppingItem(val name: String, var quantity: String,
                            var isChecked: Boolean = false)

    val url = "http://127.0.0.1:8081/shoppingList"
    var response = ""

    fun addItemToDatabase(
        productName: String, quantity: Int,
        quantityUnit: String) {



        viewModelScope.launch {

        val a = ShoppingItemDTO(productName, quantity, quantityUnit, "ABC")

        val json = Json.encodeToString(a)

        val client = HttpClient(CIO)
            val text: HttpResponse = client.request(url) {
                method = HttpMethod.Get
            }
            response = text.toString()
            //val response: HttpResponse = client.put(url) {
         //   body = json
        //}


    }

}}
