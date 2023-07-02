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

// Klasse, welche alle Methoden aus dem ShoppingListViewModel testet

class ShoppingListUnitTests {
    private lateinit var service: OFFAPIService
    private lateinit var viewModel: ShoppingListViewModel

    // Festlegung des Services, der gemockt werden soll
    @Before
    fun setup(){
        service = mockk()
    }

    // Test, ob die Methode addItemsToDatabase die Funktion addToShoppingList korrekt aufruft
    @Test
    fun `addItemsToDatabase should invoke addToShoppingList`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = ShoppingListViewModel(service, mainViewModel = MainViewModel())
        val productName = "Milch"
        val quantity = 300
        val quantityUnit = "ml"

        // Mocking des Verhaltens von service.addToShoppingList
        coEvery { service.addToShoppingList(productName, quantity, quantityUnit, "") } returns listOf(
            ShoppingItemDTO("Apfel", 3, "Stk", ""),
            ShoppingItemDTO("Banane", 2, "Stk", ""),
            ShoppingItemDTO("Milch", 300, "ml", "")
        )

        // Ausführung des Tests
        runBlocking {
            viewModel.addItemsToDatabase("Milch", 300, "ml")
        }

        // Verifizierung der richtigen Ausgabe
        coVerify { service.addToShoppingList(productName, quantity, quantityUnit, "") }

        // Zurücksetzen des Dispatchers
        Dispatchers.resetMain()
    }

    // Test, ob die Methode getItemsFromDatabase die Funktion getShoppingList korrekt aufruft
    @Test
    fun `getItemsFromDatabase should invoke getShoppingList`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = ShoppingListViewModel(service, mainViewModel = MainViewModel())

        // Mocking des Verhaltens von service.getShoppingList
        coEvery { service.getShoppingList("") } returns listOf(
            ShoppingItemDTO("Apfel", 3, "Stk", ""),
            ShoppingItemDTO("Banane", 2, "Stk", ""),
            ShoppingItemDTO("Milch", 300, "ml", "")
        )

        // Ausführung des Tests
        runBlocking {
            viewModel.getItemsFromDatabase()
        }

        // Verifizierung des Aufrufs
        coVerify { service.getShoppingList("") }

        // Überprüfung, ob die GET-Methode die richtigen Elemente ausgibt
        assertEquals("Apfel", viewModel.items[0].productName)
        assertEquals(3, viewModel.items[0].quantity)
        assertEquals("Stk", viewModel.items[0].quantityUnit)
        assertEquals("Banane", viewModel.items[1].productName)
        assertEquals(2, viewModel.items[1].quantity)
        assertEquals("Stk", viewModel.items[1].quantityUnit)
        assertEquals("Milch", viewModel.items[2].productName)
        assertEquals(300, viewModel.items[2].quantity)
        assertEquals("ml", viewModel.items[2].quantityUnit)

        Dispatchers.resetMain()
    }

    @Test
    fun `removeItemFromDatabase should invoke removeFromShoppingList`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = ShoppingListViewModel(service, mainViewModel = MainViewModel())
        val productName = "Milch"
        val quantity = 300
        val quantityUnit = "ml"

        // Mock the behavior of service.removeFromShoppingList
        coEvery { service.removeFromShoppingList(productName, "") } returns listOf(
            ShoppingItemDTO("Apfel", 3, "Stk", ""),
            ShoppingItemDTO("Banane", 2, "Stk", ""),
            ShoppingItemDTO("Milch", 300, "ml", "")
        )

        // Ausführung des Tests
        runBlocking {
            viewModel.removeItemFromDatabase(productName, quantity, quantityUnit)
        }

        // Verifizierung des korrekten Methodenaufrufs
        coVerify { service.removeFromShoppingList(productName, "") }

        // Zurücksetzen des Dispatchers
        Dispatchers.resetMain()
    }
}
