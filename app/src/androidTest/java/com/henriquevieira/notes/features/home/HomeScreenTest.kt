package com.henriquevieira.notes.features.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HomeActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testAddButton() {
        composeTestRule.onNodeWithTag(ADD_BUTTON_TAG)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
    }

    @Test
    fun testNotesList() {
        composeTestRule.onNodeWithTag(NOTES_LIST_TAG)
            .assertIsDisplayed()
            .onChildren().assertAny(
                hasText("Test Title 1")
            ).assertAny(
                hasText("Test Title 2")
            )
    }

    @Test
    fun testNoteClick() {
        composeTestRule.onNodeWithTag(NOTES_LIST_TAG)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(NOTES_LIST_TAG)
            .assertIsDisplayed()
            .onChildren().filter(
                hasText("Test Title 1")
            ).onFirst()
            .assertHasClickAction()
    }

    companion object {
        private const val NOTES_LIST_TAG = "NOTES_LIST_TAG"
        private const val ADD_BUTTON_TAG = "ADD_BUTTON_TAG"
    }
}