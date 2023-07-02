package com.prime.pantrymastergitcode
import com.prime.pantrymastergitcode.api.OFFAPIService
import com.prime.pantrymastergitcode.api.dto.PantryItemDTO
import com.prime.pantrymastergitcode.api.dto.ProductDTO
import com.prime.pantrymastergitcode.view.scanner.ScannerViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

// Diese Klasse testet alle Methoden des ScannerViewModels
class ScannerUnitTests {

    private lateinit var service: OFFAPIService
    private lateinit var viewModel: ScannerViewModel

    // Festlegung des Services, der gemockt werden soll
    @Before
    fun setup() {
        service = mockk()
    }

    // Test, ob der Wert des Produkts gesetzt wird
    @Test
    fun `setProduct should update the value of product`() {
        val viewModel = ScannerViewModel(service)

        val product = ProductDTO(
            5, 2, 50, "Produkt", "A", "", 123, 5, 2
        )

        viewModel.setProduct(product)
        assertEquals(product, viewModel.product.value)
    }

    // Test, ob der Wert des PantryProducts gesetzt wird
    @Test
    fun `setPantryProduct should update the value of pantryProduct`() {

        val viewModel = ScannerViewModel(service)

        // Erstellen eines Beispiel-PantryProducts
        val pantryProduct = PantryItemDTO(
            12345678, "ABC", "Produkt",java.time.LocalDate.now().toKotlinLocalDate(),
            java.time.LocalDateTime.now().toKotlinLocalDateTime(), 10, "Stck"
        )

        viewModel.setPantryProduct(pantryProduct)

        // Prüfung der set-Methode
        assertEquals(pantryProduct, viewModel.pantryProduct.value)
    }

    // Testet, ob die API die Produktinformationen lädt
    @Test
    fun `getProductFromAPI should update the values and return true on success`()  {
        val viewModel = ScannerViewModel(service)

        // Festhalten der aktuellen Zeit
        val expirationDate = java.time.LocalDate.now().toKotlinLocalDate()
        val appendDate = java.time.LocalDateTime.now().toKotlinLocalDateTime()

        // Erstellen eines Beispiel-Produkts
        val product = ProductDTO(
            5, 2, 50, "Produkt", "A", "", 12345678, 5, 2
        )

        // Erstellen eines Beispiel-PantryItems
        val pantryProduct = PantryItemDTO(
            12345678, "ABC", "Produkt", expirationDate,
            appendDate, 10, "Stck"
        )

        coEvery { service.postProductDetails(pantryProduct.productCode) } returns product

        // Ausführung des Tests
        runBlocking { viewModel.getProductFromAPI(pantryProduct.productCode) }

        // Erstellen einer Kopie des PantryProducts
        val expectedPantryProduct = pantryProduct.copy(
            appendDate = viewModel.pantryProduct.value.appendDate,
            userID = viewModel.pantryProduct.value.userID,
            quantity = viewModel.pantryProduct.value.quantity,
            quantityUnit = viewModel.pantryProduct.value.quantityUnit
        )

        // Überprüfung der API-Abfrage
        assertEquals(product, viewModel.product.value)
        assertEquals(expectedPantryProduct, viewModel.pantryProduct.value)
        assertEquals(true, viewModel.loaded.value)

    }

    // Testet ob Produkte zu Pantry hinzugefügt werden
    @Test
    fun `addProductToPantry should invoke postPantryEntry`() {

        // Festlegung des Dispatchers für den Main Thread
        Dispatchers.setMain(Dispatchers.Unconfined)

        val viewModel = ScannerViewModel(service)

        // Festhalten der aktuellen Zeit
        val expirationDate = java.time.LocalDate.now().toKotlinLocalDate()
        val appendDate = java.time.LocalDateTime.now().toKotlinLocalDateTime()

        // Erstellung eines Beispiel-PantryProducts
        val pantryProduct = PantryItemDTO(
            12345678, "ABC", "Produkt", expirationDate,
            appendDate, 10, "Stck"
        )

        // Erstellung eines PostPantryItemDTOs
        val postPantryItemDTO = pantryProduct.copy(
            appendDate = appendDate,
            userID = "ABC"
        )

        // Mocking der Funktion
        coEvery { service.postPantryEntry(pantryProduct) } just Runs

        // Ausführung des Tests
        runBlocking { viewModel.addProductToPantry(pantryProduct) }

        // Prüfung ob die Methode durchgeführt wurde
        coVerify { service.postPantryEntry(postPantryItemDTO) }

        // Zurücksetzen des Dispatchers
        Dispatchers.resetMain()
    }

    // Test, ob der Wert "loaded" geupdatet wird
    @Test
    fun `setLoaded should update the loaded value`() {
        // Erzeugen des ViewModels
        val viewModel = ScannerViewModel(service)

        // Aufrufen der zu testenden Methode
        viewModel.setLoaded(true)

        // Überprüfen des aktualisierten Werts
        assertEquals(true, viewModel.loaded.value)

        // Erneutes Aufrufen der Methode mit einem anderen Wert
        viewModel.setLoaded(false)

        // Überprüfen des aktualisierten Werts
        assertEquals(false, viewModel.loaded.value)
    }
}
