package com.henriquevieira.notes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
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
        composeTestRule.onNodeWithTag("TEXT_FIELD_TAG")
            .assertTextEquals("")
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("TEXT_FIELD_TAG")
            .performTextInput("Test text")

        composeTestRule.onNodeWithTag("TEXT_FIELD_TAG")
            .assertTextEquals("Test text")
            .assertIsDisplayed()
    }

}