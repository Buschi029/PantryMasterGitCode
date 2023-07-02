package com.prime.pantrymastergitcode.api

object HttpRoutes {
    private const val BASE_URL = "http://192.168.188.20:"
    private const val BASE_URL_PRODUCT = "${BASE_URL}8080/"
    private const val BASE_URL_SHOPPING = "${BASE_URL}8081/"
    const val product = "${BASE_URL_PRODUCT}product"
    const val inventory = "${BASE_URL_PRODUCT}inventory"
    const val shoppingList = "${BASE_URL_SHOPPING}shoppingList"
}
