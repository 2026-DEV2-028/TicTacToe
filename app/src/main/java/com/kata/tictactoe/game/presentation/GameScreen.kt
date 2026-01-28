package com.kata.tictactoe.game.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GameScreen() {
    GameScreenContent()
}

@Composable
fun GameScreenContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Game screen")
    }
}

@Preview
@Composable
private fun GameScreenContentPreview() {
    GameScreenContent()
}