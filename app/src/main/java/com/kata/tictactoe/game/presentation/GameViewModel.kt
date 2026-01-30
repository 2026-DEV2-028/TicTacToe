package com.kata.tictactoe.game.presentation

import androidx.lifecycle.ViewModel
import com.kata.tictactoe.R
import com.kata.tictactoe.core.helper.UiText
import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark
import com.kata.tictactoe.game.domain.model.Player
import com.kata.tictactoe.game.domain.usecase.CheckWinnerResult
import com.kata.tictactoe.game.domain.usecase.CheckWinnerUseCase
import com.kata.tictactoe.game.domain.usecase.MakeMoveUseCase
import com.kata.tictactoe.game.domain.usecase.MoveResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = GameViewModel.Factory::class)
class GameViewModel @AssistedInject constructor(
    private val makeMoveUseCase: MakeMoveUseCase,
    private val checkWinnerUseCase: CheckWinnerUseCase,
    @Assisted("firstPlayerName") private val firstPlayerName: String,
    @Assisted("secondPlayerName") private val secondPlayerName: String
) : ViewModel() {

    private val players: List<Player> = listOf(
        Player(firstPlayerName, Mark.X),
        Player(secondPlayerName, Mark.O)
    )

    private val _uiState = MutableStateFlow(
        UiState(
            currentPlayer = players.random(),
            boardState = BoardState.newEmptyBoard()
        )
    )
    val uiState = _uiState.asStateFlow()

    val nextPlayer: Player get() = players.first { it != uiState.value.currentPlayer }

    fun onUserAction(userAction: UserAction) {
        when (userAction) {
            is UserAction.OnCellClick -> handleCellClick(userAction.row, userAction.col)
            UserAction.OnReplayButtonClick -> {
                _uiState.update {
                    it.copy(
                        currentPlayer = players.random(),
                        boardState = BoardState.newEmptyBoard(),
                        endOfGameMessage = null
                    )
                }
            }
        }
    }

    private fun handleCellClick(row: Int, col: Int) {
        val makeMoveResult = makeMoveUseCase.execute(
            boardState = uiState.value.boardState,
            selectedCol = col,
            selectedRow = row,
            playerMark = uiState.value.currentPlayer.assignedMark
        )

        when (makeMoveResult) {
            is MoveResult.Success -> {
                _uiState.update {
                    it.copy(
                        currentPlayer = nextPlayer,
                        boardState = makeMoveResult.newBoardState
                    )
                }

                checkForWinner()
            }

            else -> {
                // Nothing to do
            }
        }
    }

    private fun checkForWinner() {
        val checkWinnerResult = checkWinnerUseCase.execute(uiState.value.boardState)

        when (checkWinnerResult) {
            is CheckWinnerResult.Winner -> {
                val winingPlayer = players.first { it.assignedMark == checkWinnerResult.winnerMark }
                _uiState.update {
                    it.copy(
                        endOfGameMessage = UiText.StringResource(
                            id = R.string.game_win_message,
                            args = arrayOf(winingPlayer.name)
                        )
                    )
                }
            }

            CheckWinnerResult.Draw -> {
                _uiState.update {
                    it.copy(
                        endOfGameMessage = UiText.StringResource(
                            id = R.string.game_draw_message
                        )
                    )
                }
            }

            CheckWinnerResult.InProgress -> {
                // Nothing to do
            }
        }
    }

    data class UiState(
        val currentPlayer: Player,
        val boardState: BoardState = BoardState.newEmptyBoard(),
        val endOfGameMessage: UiText? = null
    )

    sealed class UserAction {
        data class OnCellClick(val row: Int, val col: Int) : UserAction()
        data object OnReplayButtonClick : UserAction()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("firstPlayerName") firstPlayerName: String,
            @Assisted("secondPlayerName") secondPlayerName: String
        ): GameViewModel
    }
}