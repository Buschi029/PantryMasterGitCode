package com.prime.pantrymastergitcode.api

// Festlegung der HTTP-Routen für den Backendzugriff
object HttpRoutes {

    // Zugrundeliegende Basis-URL für den Backendzugriff
    private const val BASE_URL = "http://10.0.2.2:"
    private const val BASE_URL_PRODUCT = "${BASE_URL}8080/"
    private const val BASE_URL_SHOPPING = "${BASE_URL}8081/"
    const val product = "${BASE_URL_PRODUCT}product"
    const val inventory = "${BASE_URL_PRODUCT}inventory"
    const val shoppingList = "${BASE_URL_SHOPPING}shoppingList"
}
