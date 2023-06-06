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

    val url = "http://82.165.114.121/shoppingList"
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
