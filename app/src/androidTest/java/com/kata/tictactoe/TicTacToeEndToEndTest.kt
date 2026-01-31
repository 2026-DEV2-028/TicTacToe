package com.kata.tictactoe

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TicTacToeEndToEndTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun fullGameFlow_winCase() {
        // Enter first player name
        composeTestRule
            .onNodeWithTag("first_player_text_field")
            .performTextInput("Player1")

        // Enter second player name
        composeTestRule
            .onNodeWithTag("second_player_text_field")
            .performTextInput("Player2")

        // Click start button
        composeTestRule
            .onNodeWithTag("start_game_button")
            .performClick()

        // Play the game - Player X wins with top row
        // Move 1: Player X clicks cell (0,0) - top left
        composeTestRule
            .onNodeWithTag("cell_0_0")
            .performClick()

        // Player O clicks cell (1,0) - middle left
        composeTestRule
            .onNodeWithTag("cell_1_0")
            .performClick()

        // Player X clicks cell (0,1) - top center
        composeTestRule
            .onNodeWithTag("cell_0_1")
            .performClick()

        // Player O clicks cell (1,1) - center
        composeTestRule
            .onNodeWithTag("cell_1_1")
            .performClick()

        // Player X clicks cell (0,2) - top right -> WINS!
        composeTestRule
            .onNodeWithTag("cell_0_2")
            .performClick()

        // Verify win message is displayed
        composeTestRule
            .onNodeWithText("win", substring = true)
            .assertIsDisplayed()

        // Verify replay button is displayed
        composeTestRule
            .onNodeWithText("Replay")
            .assertIsDisplayed()
    }

    @Test
    fun fullGameFlow_drawCase() {
        // Enter first player name
        composeTestRule
            .onNodeWithTag("first_player_text_field")
            .performTextInput("Player1")

        // Enter second player name
        composeTestRule
            .onNodeWithTag("second_player_text_field")
            .performTextInput("Player2")

        // Click start button
        composeTestRule
            .onNodeWithTag("start_game_button")
            .performClick()

        // Play the game - Draw scenario
        // Move 1: Player X clicks cell (0,0) - top left
        composeTestRule
            .onNodeWithTag("cell_0_0")
            .performClick()

        // Move 2: Player O clicks cell (0,1) - top center
        composeTestRule
            .onNodeWithTag("cell_0_1")
            .performClick()

        // Move 3: Player X clicks cell (0,2) - top right
        composeTestRule
            .onNodeWithTag("cell_0_2")
            .performClick()

        // Move 4: Player O clicks cell (1,1) - center
        composeTestRule
            .onNodeWithTag("cell_1_1")
            .performClick()

        // Move 5: Player X clicks cell (1,0) - middle left
        composeTestRule
            .onNodeWithTag("cell_1_0")
            .performClick()

        // Move 6: Player O clicks cell (1,2) - middle right
        composeTestRule
            .onNodeWithTag("cell_1_2")
            .performClick()

        // Move 7: Player X clicks cell (2,1) - bottom center
        composeTestRule
            .onNodeWithTag("cell_2_1")
            .performClick()

        // Move 8: Player O clicks cell (2,0) - bottom left
        composeTestRule
            .onNodeWithTag("cell_2_0")
            .performClick()

        // Move 9: Player X clicks cell (2,2) - bottom right -> DRAW!
        composeTestRule
            .onNodeWithTag("cell_2_2")
            .performClick()

        // Verify draw message is displayed
        composeTestRule
            .onNodeWithText("draw", substring = true)
            .assertIsDisplayed()

        // Verify replay button is displayed
        composeTestRule
            .onNodeWithText("Replay")
            .assertIsDisplayed()
    }
}
