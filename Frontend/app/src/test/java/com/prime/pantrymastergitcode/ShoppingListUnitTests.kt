package com.prime.pantrymastergitcode
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.ShoppingItemDTO
import com.prime.pantrymastergitcode.view.shoppingList.ShoppingListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
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
    fun `addItemsToDatabase should invoke addToShoppingList`() = runBlocking {
        // Arrange
        val viewModel = ShoppingListViewModel(service)
        val productName = "Product"
        val quantity = 2
        val quantityUnit = "unit"

        // Define the expected behavior
        coEvery { service.addToShoppingList(productName, quantity, quantityUnit, "") } returns 

        // Act
        viewModel.addItemsToDatabase(productName, quantity, quantityUnit)

        // Assert
        coVerifyAll { service.addToShoppingList(productName, quantity, quantityUnit, "") }
    }

    /*
    fun addItemsToDatabase(productName: String, quantity: Int, quantityUnit: String) {
        viewModelScope.launch {
            try {
                items = service.addToShoppingList(productName, quantity, quantityUnit, "")!!
            } catch (e: Exception) {
                Log.e("ShoppingListViewModel", e.toString())
            }
        }
    }
*/

    @Test
    fun `getItemsFromDatabase should update items`() = runBlocking {
        val items = mutableListOf(ShoppingItemDTO("Item 1", 1, "unit", ""))

        coEvery { service.getShoppingList("") } returns items

        viewModel.getItemsFromDatabase()

        assertEquals(items, viewModel.items)
    }

    @Test
    fun `addItemsToDatabase should update items`() = runBlocking {
        val productName = "Product"
        val quantity = 2
        val quantityUnit = "unit"
        val items = mutableListOf(ShoppingItemDTO("Item 1", 1, "unit", ""))

        coEvery { service.addToShoppingList(productName, quantity, quantityUnit, "") } returns items

        viewModel.addItemsToDatabase(productName, quantity, quantityUnit)

        assertEquals(items, viewModel.items)
    }

    @Test
    fun `removeItemFromDatabase should update items`() = runBlocking {
        val productName = "Product"
        val items = mutableListOf(ShoppingItemDTO("Item 1", 1, "unit", ""))

        coEvery { service.removeFromShoppingList(productName, "") } returns items

        viewModel.removeItemFromDatabase(productName, 0, "")

        assertEquals(items, viewModel.items)
    }
}
