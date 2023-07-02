package com.prime.pantrymastergitcode

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prime.pantrymastergitcode.api.OFFAPIService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScannerViewUITest {
    //Service für Backendabfragen intitialisieren, da dieser zum starten der App benötigt wird
    private val service = OFFAPIService.create()

    //Definierung der compose Rule um mit den compose Elementen interagieren zu können
    @get:Rule
    val composeTestRule = createComposeRule()

    //starten der App zum testen
    @Before
    fun setup(){
        composeTestRule.launchAPP(service)
    }
    @Test
    fun scannerViewTest() {
        //definieren der Elemente, die der Text verwendet
        val scannerIcon = composeTestRule.onNodeWithTag("Icon0")
        val productNameInput = composeTestRule.onNodeWithTag("ProductNameInput")
        val quantityInput = composeTestRule.onNodeWithTag("QuantityInput")
        val unitInput = composeTestRule.onNodeWithTag("UnitInput")
        val addToPantryButton = composeTestRule.onNodeWithTag("AddToPantryButton")

        //navigieren zum Viewm ScannerView
        scannerIcon.performClick()
        //eingabe von variablen in die textfelder
        productNameInput.performTextInput("Maggi")
        quantityInput.performTextInput("2")
        unitInput.performTextInput("Btl")
        //sicherstellen dass button zum hinzufügen in pantry angezeigt wird
        addToPantryButton.assertIsDisplayed()
        //klicken des Buttons
        addToPantryButton.performClick()
        //testen ob Textfelder wieder zurück gesetzt wurden
        productNameInput.assertTextContains("")
        quantityInput.assertTextContains("")
        unitInput.assertTextContains("")
    }
}
