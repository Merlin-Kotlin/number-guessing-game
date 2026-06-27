package com.example.numberguessinggame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    if (gameUiState.isGameOver) {
        GameOverScreen(
            attempts = gameUiState.attempts,
            onPlayAgain = { gameViewModel.resetGame() }
        )
    } else {
        GamePlayScreen(
            gameUiState = gameUiState,
            onGuessChanged = { gameViewModel.updateUserGuess(it) },
            onSubmitGuess = { gameViewModel.checkUserGuess() }
        )
    }
}

@Composable
fun GamePlayScreen(
    gameUiState: GameUiState,
    onGuessChanged: (String) -> Unit,
    onSubmitGuess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Guess a number between 1 and 100",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Attempts: ${gameUiState.attempts}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier =Modifier.height(16.dp))

        OutlinedTextField(
            value = gameUiState.userGuess,
            onValueChange = onGuessChanged,
            label = { Text("Your Guess") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = gameUiState.hint,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSubmitGuess) {
            Text("Submit Guess")
        }
    }
}

@Composable
fun GameOverScreen(
    attempts: Int,
    onPlayAgain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You Got It!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You guessed it in $attempts attempts!",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onPlayAgain) {
            Text("Play Again")
        }
    }
}