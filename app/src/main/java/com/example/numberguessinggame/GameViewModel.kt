package com.example.numberguessinggame

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun updateUserGuess(guess: String) {
        _uiState.update { it.copy(userGuess = guess) }
    }

    fun checkUserGuess() {
        val guess = _uiState.value.userGuess.toIntOrNull()
        if (guess == null) {
            _uiState.update { it.copy(hint = "Please enter a valid number!") }
            return
        }
        val currentState = _uiState.value
        val newAttempts = currentState.attempts + 1
        when {
            guess < currentState.targetNumber -> {
                _uiState.update { it.copy(
                    hint = "Too Low!",
                    attempts = newAttempts,
                    userGuess = ""
                )}
            }
            guess > currentState.targetNumber -> {
                _uiState.update { it.copy(
                    hint = "Too High!",
                    attempts = newAttempts,
                    userGuess = ""
                )}
            }
            else -> {
                _uiState.update { it.copy(
                    hint = "Correct!",
                    attempts = newAttempts,
                    isGameOver = true,
                    userGuess = ""
                )}
            }
        }
    }
    fun resetGame() {
        _uiState.value = GameUiState()
    }
}