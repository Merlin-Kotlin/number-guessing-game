package com.example.numberguessinggame

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var gameViewModel: GameViewModel
    @Before
    fun setup() {
        gameViewModel = GameViewModel()
    }
    @Test
    fun gameViewModel_CorrectGuess_ScoreUpdatedAndGameOver() {

        val currentState = gameViewModel.uiState.value

        val targetNumber = currentState.targetNumber

        gameViewModel.updateUserGuess(targetNumber.toString())

        gameViewModel.checkUserGuess()

        val updatedState = gameViewModel.uiState.value

        assertTrue(
            "Game should be over after correct guess",
            updatedState.isGameOver
        )
        assertEquals(
            "Attempts should be 1 after one guess",
            1,
            updatedState.attempts
        )
    }
    @Test
    fun gameViewModel_WrongGuess_CorrectHintShown() {
        val currentState = gameViewModel.uiState.value
        val targetNumber = currentState.targetNumber

        val wrongGuess = if (targetNumber > 1) targetNumber - 1
        else targetNumber + 1

        gameViewModel.updateUserGuess(wrongGuess.toString())

        gameViewModel.checkUserGuess()

        val updatedState = gameViewModel.uiState.value

        assertFalse(
            "Game should not be over after wrong guess",
            updatedState.isGameOver
        )
        assertTrue(
            "Hint should be shown after wrong guess",
            updatedState.hint.isNotEmpty()
        )
    }
    @Test
    fun gameViewModel_EmptyGuess_ErrorMessageShown() {
        gameViewModel.updateUserGuess("")

        gameViewModel.checkUserGuess()

        val updatedState = gameViewModel.uiState.value

        assertFalse(
            "Game should not be over after empty guess",
            updatedState.isGameOver
        )
        assertEquals(
            "Should show invalid number message",
            "Please enter a valid number!",
            updatedState.hint
        )
        assertEquals(
            "Attempts should not increase for invalid guess",
            0,
            updatedState.attempts
        )
    }
}