package com.example.numberguessinggame

data class GameUiState(
    val targetNumber: Int = (1..100).random(),
    val userGuess: String = "",
    val attempts: Int = 0,
    val hint: String = "",
    val isGameOver: Boolean = false
)