package com.henriquevieira.notes.features.main

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.utils.assertNoteType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NoteScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<NoteActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testTitleField() {
        with(composeTestRule) {
            onNodeWithTag(TITLE_TAG)
                .assertIsDisplayed()
                .performTextInput("Test Title")

            onNodeWithTag(TITLE_TAG)
                .assertTextEquals("Test Title")
        }

    }

    @Test
    fun testTextField() {
        with(composeTestRule) {
            onNodeWithTag(TEXT_FIELD_TAG)
                .assertTextEquals("")
                .assertIsDisplayed()

            onNodeWithTag(TEXT_FIELD_TAG)
                .performTextInput("Test text")

            onNodeWithTag(TEXT_FIELD_TAG)
                .assertTextEquals("Test text")
                .assertIsDisplayed()
        }
    }

    @Test
    fun testClearButton() {
        with(composeTestRule) {
            onNodeWithTag(CLEAR_TEXT_BUTTON_TAG)
                .assertIsDisplayed()
                .assertHasClickAction()

            onNodeWithTag(TEXT_FIELD_TAG)
                .performTextInput("Test text")

            onNodeWithTag(TEXT_FIELD_TAG)
                .assertTextEquals("Test text")

            onNodeWithTag(CLEAR_TEXT_BUTTON_TAG)
                .performClick()

            onNodeWithTag(TITLE_TAG)
                .assertTextEquals("")
                .assertIsDisplayed()
        }
    }

    @Test
    fun testSaveButton() {
        composeTestRule.onNodeWithTag(SAVE_BUTTON_TAG)
            .assertHasClickAction()
            .assertIsDisplayed()
    }

    @Test
    fun testPrimaryColorButton() {
        composeTestRule.onNodeWithTag(PRIMARY_COLOR_BUTTON_TAG)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        composeTestRule.onNodeWithTag(TEXT_FIELD_TAG)
            .assertNoteType(NoteTypes.Primary.toString())
    }

    @Test
    fun testRedColorButton() {
        composeTestRule.onNodeWithTag(RED_COLOR_BUTTON_TAG)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        composeTestRule.onNodeWithTag(TEXT_FIELD_TAG)
            .assertNoteType(NoteTypes.Red.toString())
    }

    @Test
    fun testGreenColorButton() {
        composeTestRule.onNodeWithTag(GREEN_COLOR_BUTTON_TAG)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        composeTestRule.onNodeWithTag(TEXT_FIELD_TAG)
            .assertNoteType(NoteTypes.Green.toString())
    }

    @Test
    fun testYellowColorButton() {
        composeTestRule.onNodeWithTag(YELLOW_COLOR_BUTTON_TAG)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        composeTestRule.onNodeWithTag(TEXT_FIELD_TAG)
            .assertNoteType(NoteTypes.Yellow.toString())
    }

    @Test
    fun testBlueColorButton() {
        composeTestRule.onNodeWithTag(BLUE_COLOR_BUTTON_TAG)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        composeTestRule.onNodeWithTag(TEXT_FIELD_TAG)
            .assertNoteType(NoteTypes.Blue.toString())
    }

    companion object {
        private const val TITLE_TAG = "TITLE_TAG"
        private const val TEXT_FIELD_TAG = "TEXT_FIELD_TAG"
        private const val CLEAR_TEXT_BUTTON_TAG = "CLEAR_TEXT_BUTTON_TAG"
        private const val SAVE_BUTTON_TAG = "SAVE_BUTTON_TAG"
        private const val PRIMARY_COLOR_BUTTON_TAG = "PRIMARY_COLOR_BUTTON_TAG"
        private const val RED_COLOR_BUTTON_TAG = "RED_COLOR_BUTTON_TAG"
        private const val GREEN_COLOR_BUTTON_TAG = "GREEN_COLOR_BUTTON_TAG"
        private const val YELLOW_COLOR_BUTTON_TAG = "YELLOW_COLOR_BUTTON_TAG"
        private const val BLUE_COLOR_BUTTON_TAG = "BLUE_COLOR_BUTTON_TAG"
    }
}