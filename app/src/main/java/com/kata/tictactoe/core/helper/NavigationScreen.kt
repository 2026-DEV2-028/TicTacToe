package com.kata.tictactoe.core.helper

sealed class NavigationScreen {
    data object Home : NavigationScreen()
    data class Game(
        val firstPlayerName: String,
        val secondPlayerName: String
    ) : NavigationScreen()
}