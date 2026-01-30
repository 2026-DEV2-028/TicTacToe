package com.kata.tictactoe.game.presentation

import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark
import com.kata.tictactoe.game.domain.usecase.CheckWinnerUseCase
import com.kata.tictactoe.game.domain.usecase.MakeMoveUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class GameViewModelTest {

    private fun createViewModel(
        firstPlayerName: String = "Player1",
        secondPlayerName: String = "Player2"
    ): GameViewModel {
        return GameViewModel(
            makeMoveUseCase = MakeMoveUseCase(),
            checkWinnerUseCase = CheckWinnerUseCase(),
            firstPlayerName = firstPlayerName,
            secondPlayerName = secondPlayerName
        )
    }

    @Test
    fun `initial state has empty board and no end of game message`() {
        val viewModel = createViewModel()

        val uiState = viewModel.uiState.value

        assertEquals(BoardState.newEmptyBoard(), uiState.boardState)
        assertNull(uiState.endOfGameMessage)
    }

    @Test
    fun `initial state has first player with X mark`() {
        val viewModel = createViewModel()

        val currentPlayer = viewModel.uiState.value.currentPlayer

        assertEquals(Mark.X, currentPlayer.assignedMark)
    }

    @Test
    fun `on cell click with valid move updates board and switches player`() {
        val viewModel = createViewModel()
        val initialPlayer = viewModel.uiState.value.currentPlayer

        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 0))

        val uiState = viewModel.uiState.value
        assertEquals(Mark.X, uiState.boardState.cells[0][0])
        assertNotEquals(uiState.currentPlayer, initialPlayer)
    }

    @Test
    fun `on cell click on already marked cell does not change state`() {
        val viewModel = createViewModel()
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 0))
        val stateAfterFirstClick = viewModel.uiState.value

        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 0))

        assertEquals(stateAfterFirstClick, viewModel.uiState.value)
    }

    @Test
    fun `winning move sets end of game message`() {
        val viewModel = createViewModel()

        // X plays (0,0)
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 0))
        // O plays (1,0)
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 0))
        // X plays (0,1)
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 1))
        // O plays (1,1)
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 1))
        // X plays (0,2) - X wins with top row
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 2))

        assertNotNull(viewModel.uiState.value.endOfGameMessage)
    }

    @Test
    fun `draw game sets end of game message`() {
        val viewModel = createViewModel()

        // Fill the board in a way that results in a draw
        // X | O | X
        // X | O | O
        // O | X | X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 0)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 1)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 2)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 1)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 0)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 2)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 2, col = 1)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 2, col = 0)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 2, col = 2)) // X

        assertNotNull(viewModel.uiState.value.endOfGameMessage)
    }

    @Test
    fun `on replay button click resets the game`() {
        val viewModel = createViewModel()

        // Fill the board in a way that results in a draw
        // X | O | X
        // X | O | O
        // O | X | X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 0)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 1)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 0, col = 2)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 1)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 0)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 1, col = 2)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 2, col = 1)) // X
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 2, col = 0)) // O
        viewModel.onUserAction(GameViewModel.UserAction.OnCellClick(row = 2, col = 2)) // X

        // Replay
        viewModel.onUserAction(GameViewModel.UserAction.OnReplayButtonClick)

        val uiState = viewModel.uiState.value
        assertEquals(BoardState.newEmptyBoard(), uiState.boardState)
        assertNull(uiState.endOfGameMessage)
        assertEquals(Mark.X, uiState.currentPlayer.assignedMark)
    }
}