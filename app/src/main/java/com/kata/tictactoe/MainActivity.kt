package com.kata.tictactoe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.kata.tictactoe.game.presentation.GameScreen
import com.kata.tictactoe.home.presentation.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }

    data object Home
    data class Game(
        val firstPlayerName: String,
        val secondPlayerName: String
    )

    @Composable
    fun App() {
        val backStack = remember { mutableStateListOf<Any>(Home) }
        NavDisplay(
            modifier = Modifier.systemBarsPadding(),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = { key ->
                when (key) {
                    Home -> NavEntry(key) {
                        HomeScreen(
                            onStartGame = { firstPlayerName, secondPlayerName ->
                                backStack.add(
                                    Game(
                                        firstPlayerName = firstPlayerName,
                                        secondPlayerName = secondPlayerName
                                    )
                                )
                            })
                    }

                    is Game -> NavEntry(key) {
                        GameScreen(
                            firstPlayerName = key.firstPlayerName,
                            secondPlayerName = key.secondPlayerName
                        )
                    }

                    else -> throw IllegalStateException()
                }
            })
    }
}