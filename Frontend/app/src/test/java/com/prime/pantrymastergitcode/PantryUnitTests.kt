package com.prime.pantrymastergitcode
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import com.prime.pantrymastergitcode.view.pantry.PantryViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


// Diese Klasse testet alle Methoden des PantryViewModels
class PantryUnitTests {
    private lateinit var service: OFFAPIService

    // Festlegung des Services, der gemockt werden soll
    @Before
    fun setup() {
        service = mockk()
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @Test
    fun `getPantryItemsFromDatabase should invoke getPantryList`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        coEvery { service.getPantryList("ABC") } returns mutableListOf(
            PantryItemDTO(
                123, "ABC", "Apfel", java.time.LocalDate.now().toKotlinLocalDate(),
                java.time.LocalDateTime.now().toKotlinLocalDateTime(), 1, "Stck"
            ),
            PantryItemDTO(
                456, "ABC", "Banane", java.time.LocalDate.now().toKotlinLocalDate(),
                java.time.LocalDateTime.now().toKotlinLocalDateTime(), 2, "Stck"
            )
        )

        // Ausführung des Tests
        runBlocking {
            viewModel.getPantryItemsFromDatabase()
        }

        // Verifizierung des Aufrufs
        coVerify { service.getPantryList("ABC") }

        assertEquals(123, viewModel.items[0].productCode)
        assertEquals(2, viewModel.items[1].quantity)

        Dispatchers.resetMain()
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @Test
    fun `getProductDetails should invoke postProductDetails`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        coEvery { service.postProductDetails(123) } returns ProductDTO(
            5, 2, 50, "Produkt", "A", "", 123, 5, 2
        )

        // Ausführung des Tests
        runBlocking {
            viewModel.getProductDetails(123, "Produkt")
        }

        // Verifizierung des Aufrufs
        coVerify { service.postProductDetails(123) }

        assertEquals(5, viewModel.product.value.carbohydrates)
        assertEquals(2, viewModel.product.value.fat)
        assertEquals(50, viewModel.product.value.kcal)
        assertEquals("Produkt", viewModel.product.value.name)
        assertEquals("A", viewModel.product.value.nutriscore)
        assertEquals("", viewModel.product.value.pictureLink)
        assertEquals(123, viewModel.product.value.productcode)
        assertEquals(5, viewModel.product.value.protein)
        assertEquals(2, viewModel.product.value.sugar)

        // Zurücksetzen des Dispatchers
        Dispatchers.resetMain()
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @Test
    fun `removeItemFromDatabase should invoke removeFromPantryList`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        // Festlegung der Variablen für das PantryItemDTO
        val productCode: Long = 123
        val userID = ""
        val productName ="Produkt"
        val expirationDate = java.time.LocalDate.now().toKotlinLocalDate()
        val appendDate = java.time.LocalDateTime.now().toKotlinLocalDateTime()
        val quantity = 1
        val quantityUnit = "g"

        // Festlegung einer Instanz
        val exampleDTO = PantryItemDTO(12345678, "ABC", "Apfel",java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalDateTime.now().toKotlinLocalDateTime(), 10, "Stck"
        )

        // Mocking des Verhalts von removeFromPantryList
        coEvery { service.removeFromPantryList(pantryItemDTO = PantryItemDTO(productCode,
        userID,productName, expirationDate, appendDate, quantity, quantityUnit)) } returns mutableListOf(
            PantryItemDTO(productCode,
                userID,productName, expirationDate, appendDate, quantity, quantityUnit)
        )

        // Ausführung des Tests
        runBlocking {
            viewModel.removeItemFromDatabase(exampleDTO)
        }

        // Verifizierung des korrekten Methodenaufrufs
        coVerify{ service.removeFromPantryList(exampleDTO) }

        // Zurücksetzen des Dispatchers
        Dispatchers.resetMain()
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @Test
    fun `setProductDetails should update showProductDetails state`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        // Festlegung des Booleans der übergeben wird
        val expectedValue = true

        viewModel.setProductDetails(true)

        // Überprüfung, ob die Methode den showProductDetails-Zustand richtig aktualisiert
        assertEquals(expectedValue, viewModel.showProductDetails.value)

        // Zurücksetzen des Dispatchers
        Dispatchers.resetMain()
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @Test
    fun `updatePantryItem should update pantryItems and invoke updatePantryItem`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        // Erstellen von zwei Beispiel-PantryItemDTOs

        val pantryItem = PantryItemDTO(
            12345678, "ABC", "Apfel",
            java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalDateTime.now().toKotlinLocalDateTime(),
            10, "Stck"
        )

        val pantryItem2 = PantryItemDTO(
            123456789, "DEF", "Banane",
            java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalDateTime.now().toKotlinLocalDateTime(),
            5, "Stck"
        )

        coEvery { service.updatePantryItem(pantryItem) } returns mutableListOf(
            pantryItem, pantryItem2
        )

        val updatedQuantity = 11
        pantryItem.quantity = updatedQuantity
        val index = 0

        // Hinzufügen der Pantry Items zur Liste "items"
        viewModel.items.add(pantryItem)
        viewModel.items.add(pantryItem2)


        // Ausführung des Tests
        runBlocking {
            viewModel.updatePantryItem(pantryItem, index)
        }

        // Überprüfung, ob das pantryItem an der richtigen Position aktualisiert wurde
        assertEquals(pantryItem, viewModel.items[index])

        // Verifizierung des Aufrufs
        coVerify{ service.updatePantryItem(pantryItem) }

        // Zurücksetzen des Dispatchers
        Dispatchers.resetMain()
    }

    @Test
    fun `getPantryList should return the correct items`() {
        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        // Erstellen von Beispiel-PantryItemDTOs
        val pantryItem1 = PantryItemDTO(12345678, "ABC", "Apfel",
            java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalDateTime.now().toKotlinLocalDateTime(),
            10, "Stck")
        val pantryItem2 = PantryItemDTO(123456789, "DEF", "Banane",
            java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalDateTime.now().toKotlinLocalDateTime(),
            5, "Stck")

        // Hinzufügen der PantryItemDTOs zur ViewModel-Liste
        viewModel.items.add(pantryItem1)
        viewModel.items.add(pantryItem2)

        // Aufruf der getPantryList-Methode und Überprüfung der zurückgegebenen Liste
        assertEquals(listOf(pantryItem1, pantryItem2), viewModel.getPantryList())
    }

    @Test
    fun `sortList should sort items by expirationDate`() {
        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        // Erstellen von Beispiel-PantryItemDTOs mit unterschiedlichen expirationDate und appendDate
        val pantryItem1 = PantryItemDTO(1, "ABC", "Apfel", LocalDate(2023, 7, 1), LocalDateTime(2023, 7, 1, 10, 0), 1, "g")
        val pantryItem2 = PantryItemDTO(2, "DEF", "Banane", LocalDate(2023, 7, 3), LocalDateTime(2023, 7, 2, 15, 0), 2, "ml")
        val pantryItem3 = PantryItemDTO(3, "GHI", "Kartoffel", LocalDate(2023, 7, 2), LocalDateTime(2023, 7, 3, 12, 0), 3, "Stck")

        // Hinzufügen der PantryItemDTOs zur ViewModel-Liste
        viewModel.items.addAll(listOf(pantryItem1, pantryItem2, pantryItem3))

        // Aufruf der sortList-Methode
        viewModel.sortList()

        // Überprüfung der sortierten Reihenfolge nach expirationDate
        assertEquals(listOf(pantryItem1, pantryItem3, pantryItem2), viewModel.items)
    }

    // Test ob der String verfügbar ist
    @Test
    fun `checkAvailability with any String `() {
        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        var value = "z"
        var expected = "N/A"
        var test = viewModel.checkAvailability(value)
        assertEquals(expected, test)

        value = "y"
        expected = value
        test = viewModel.checkAvailability(value)
        assertEquals(expected, test)
    }

    // Test ob der Int verfügbar ist
    @Test
    fun `checkAvailability with any Int`() {
        val viewModel = PantryViewModel(service, mainViewModel = MainViewModel())

        var value = 9999
        var expected = "N/A"
        var test = viewModel.checkAvailability(value)
        assertEquals(expected, test)

        value = 1234
        expected = value.toString()
        test = viewModel.checkAvailability(value)
        assertEquals(expected, test)
    }
}




