package com.kata.tictactoe.game.presentation

import androidx.lifecycle.ViewModel
import com.kata.tictactoe.game.domain.model.Mark
import com.kata.tictactoe.game.domain.model.Player
import com.kata.tictactoe.home.presentation.HomeViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = GameViewModel.Factory::class)
class GameViewModel @AssistedInject constructor(
    @Assisted("firstPlayerName") private val firstPlayerName: String,
    @Assisted("secondPlayerName") private val secondPlayerName: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<HomeViewModel.Event>()
    val event = _event.asSharedFlow()

    private val players: List<Player> = listOf(
        Player(firstPlayerName, Mark.X),
        Player(secondPlayerName, Mark.O)
    ).shuffled()

    init {
        _uiState.update {
            it.copy(currentPlayer = players.first())
        }
    }

    fun onUserAction(userAction: UserAction) {

    }

    data class UiState(
        val currentPlayer: Player? = null
    )

    sealed class UserAction {

    }

    sealed class Event {

    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("firstPlayerName") firstPlayerName: String,
            @Assisted("secondPlayerName") secondPlayerName: String
        ): GameViewModel
    }
}