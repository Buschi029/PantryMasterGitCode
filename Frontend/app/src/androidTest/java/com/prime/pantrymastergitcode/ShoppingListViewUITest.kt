package com.prime.pantrymastergitcode

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prime.pantrymastergitcode.api.OFFAPIService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingListViewUITest {
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
    fun shoppingListViewTest() {
        //definieren der Elemente die der Test verwendet
        val pantryIcon = composeTestRule.onNodeWithTag("Icon2")
        val headline = composeTestRule.onNodeWithTag("Headline")
        val headlineIcone = composeTestRule.onNodeWithTag("HeadlineIcon")
        val progressIndicator = composeTestRule.onNodeWithTag("ProgressIndicator")
        val shoppingList = composeTestRule.onNodeWithTag("ShopppingList")

        //navigieren in PantryView
        pantryIcon.performClick()
        //überprüfen ob anzuzeigende Elemente gezeigt werden
        headline.assertIsDisplayed()
        headlineIcone.assertIsDisplayed()
        progressIndicator.assertIsDisplayed()
        //überprüfen ob auszublendende Elemente fehlen
        shoppingList.assertDoesNotExist()
    }
}
