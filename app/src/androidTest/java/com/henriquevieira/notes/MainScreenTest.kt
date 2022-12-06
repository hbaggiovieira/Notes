package com.henriquevieira.notes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.henriquevieira.notes.features.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testTitle() {
        composeTestRule.onNodeWithTag("TITLE_TAG")
            .assertIsDisplayed()
            .assertTextEquals("My Notes")
            .assertExists()
    }

    @Test
    fun testTextField() {
        with(composeTestRule) {
            onNodeWithTag("TEXT_FIELD_TAG")
                .assertTextEquals("")
                .assertIsDisplayed()

            onNodeWithTag("TEXT_FIELD_TAG")
                .performTextInput("Test text")

            onNodeWithTag("TEXT_FIELD_TAG")
                .assertTextEquals("Test text")
                .assertIsDisplayed()
        }
    }

    @Test
    fun testClearButton() {
        with(composeTestRule) {
            onNodeWithTag("CLEAR_TEXT_BUTTON_TAG")
                .assertIsDisplayed()
                .assertHasClickAction()

            onNodeWithTag("TEXT_FIELD_TAG")
                .performTextInput("Test text")

            onNodeWithTag("TEXT_FIELD_TAG")
                .assertTextEquals("Test text")

            onNodeWithTag("CLEAR_TEXT_BUTTON_TAG")
                .performClick()

            onNodeWithTag("TEXT_FIELD_TAG")
                .assertTextEquals("")
                .assertIsDisplayed()
        }
    }

    @Test
    fun testSaveButton() {
        composeTestRule.onNodeWithTag("SAVE_BUTTON_TAG")
            .assertHasClickAction()
            .assertIsDisplayed()
    }
}