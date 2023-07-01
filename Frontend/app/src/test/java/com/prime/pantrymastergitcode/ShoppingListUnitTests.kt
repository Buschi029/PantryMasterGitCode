package com.prime.pantrymastergitcode
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Test

class ShoppingListUnitTests {
    private lateinit var service: OFFAPIService
    private lateinit var viewModel: ShoppingListViewModel

    @Before
    fun setup(){
        service = mockk()
    }

    @Test
    fun `addItemsToDatabase should invoke addToShoppingList`() {
        // Arrange
        Dispatchers.setMain(Dispatchers.Unconfined) // Setze den Dispatchers.Main auf Unconfined

        val viewModel = ShoppingListViewModel(service)
        val productName = "Milch"
        val quantity = 300
        val quantityUnit = "ml"

        // Mock the behavior of service.addToShoppingList
        coEvery { service.addToShoppingList(productName, quantity, quantityUnit, "") } returns listOf(
            ShoppingItemDTO("Apfel", 3, "Stk", ""),
            ShoppingItemDTO("Banane", 2, "Stk", ""),
            ShoppingItemDTO("Milch", 300, "ml", "")
        )

        // Act
        runBlocking {
            viewModel.addItemsToDatabase("Milch", 300, "ml")
        }

        // Verify that addToShoppingList was invoked with the correct parameters
        coVerify { service.addToShoppingList(productName, quantity, quantityUnit, "") }

        // Zurücksetzen des Dispatchers.Main
        Dispatchers.resetMain()
    }

    @Test
    fun `getItemsFromDatabase should invoke getShoppingList`() {
        // Arrange
        Dispatchers.setMain(Dispatchers.Unconfined) // Setze den Dispatchers.Main auf Unconfined

        val viewModel = ShoppingListViewModel(service)

        // Mock the behavior of service.getShoppingList
        coEvery { service.getShoppingList("") } returns listOf(
            ShoppingItemDTO("Apfel", 3, "Stk", ""),
            ShoppingItemDTO("Banane", 2, "Stk", ""),
            ShoppingItemDTO("Milch", 300, "ml", "")
        )

        // Act
        runBlocking {
            viewModel.getItemsFromDatabase()
        }

        // Verify that getShoppingList was invoked
        coVerify { service.getShoppingList("") }

        // Assert that the items property is updated with the expected values
        assertEquals("Apfel", viewModel.items[0].productName)
        assertEquals(3, viewModel.items[0].quantity)
        assertEquals("Stk", viewModel.items[0].quantityUnit)
        assertEquals("Banane", viewModel.items[1].productName)
        assertEquals(2, viewModel.items[1].quantity)
        assertEquals("Stk", viewModel.items[1].quantityUnit)
        assertEquals("Milch", viewModel.items[2].productName)
        assertEquals(300, viewModel.items[2].quantity)
        assertEquals("ml", viewModel.items[2].quantityUnit)

        // Zurücksetzen des Dispatchers.Main
        Dispatchers.resetMain()
    }



    @Test
    fun `removeItemFromDatabase should invoke removeFromShoppingList`() {
        // Arrange
        Dispatchers.setMain(Dispatchers.Unconfined) // Setze den Dispatchers.Main auf Unconfined

        val viewModel = ShoppingListViewModel(service)
        val productName = "Milch"
        val quantity = 300
        val quantityUnit = "ml"

        // Mock the behavior of service.removeFromShoppingList
        coEvery { service.removeFromShoppingList(productName, "") } returns listOf(
            ShoppingItemDTO("Apfel", 3, "Stk", ""),
            ShoppingItemDTO("Banane", 2, "Stk", ""),
            ShoppingItemDTO("Milch", 300, "ml", "")
        )

        // Act
        runBlocking {
            viewModel.removeItemFromDatabase(productName, quantity, quantityUnit)
        }

        // Verify that removeFromShoppingList was invoked with the correct parameters
        coVerify { service.removeFromShoppingList(productName, "") }

        // Zurücksetzen des Dispatchers.Main
        Dispatchers.resetMain()
    }
}
