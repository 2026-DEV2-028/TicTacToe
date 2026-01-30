package com.kata.tictactoe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.kata.tictactoe.core.helper.NavigationScreen
import com.kata.tictactoe.game.presentation.GameScreen
import com.kata.tictactoe.home.presentation.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                App()
            }
        }
    }

    @Composable
    fun App() {
        val backStack = remember { mutableStateListOf<NavigationScreen>(NavigationScreen.Home) }
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
                    NavigationScreen.Home -> NavEntry(key) {
                        HomeScreen(
                            onStartGame = { firstPlayerName, secondPlayerName ->
                                backStack.add(
                                    NavigationScreen.Game(
                                        firstPlayerName = firstPlayerName,
                                        secondPlayerName = secondPlayerName
                                    )
                                )
                            })
                    }

                    is NavigationScreen.Game -> NavEntry(key) {
                        GameScreen(
                            firstPlayerName = key.firstPlayerName,
                            secondPlayerName = key.secondPlayerName
                        )
                    }

                    else -> throw IllegalStateException("Unknown navigation key: $key")
                }
            })
    }
}