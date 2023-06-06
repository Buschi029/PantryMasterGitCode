package com.prime.pantrymastergitcode.view.shoppingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


class ShoppingListViewModel : ViewModel() {
    data class ShoppingItem(val name: String, var quantity: String,
                            var isChecked: Boolean = false)

    val url = "http://shoppinglistbackend.buschi029.repl.co/shoppingList"
    var response = ""

    fun addItemToDatabase(
        productName: String, quantity: String,
        quantityUnit: String) {



        viewModelScope.launch {

        val a = ShoppingItemDTO(productName, quantity, quantityUnit, "ABC")

        val json = Json.encodeToString(a)

        val client = HttpClient(CIO)
            response = client.get(url).toString()

            //val response: HttpResponse = client.put(url) {
         //   body = json
        //}


    }

}}
