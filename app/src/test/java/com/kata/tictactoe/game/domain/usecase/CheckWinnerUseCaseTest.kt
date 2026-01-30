package com.kata.tictactoe.game.domain.usecase

import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark
import org.junit.Assert.assertEquals
import org.junit.Test

class CheckWinnerUseCaseTest {

    private val checkWinnerUseCase = CheckWinnerUseCase()

    @Test
    fun `should return Winner when X wins by row`() {
        val board = BoardState(
            cells = listOf(
                listOf(Mark.X, Mark.X, Mark.X),
                listOf(Mark.O, Mark.O, null),
                listOf(null, null, null)
            )
        )
        val result = checkWinnerUseCase.execute(board)
        assertEquals(CheckWinnerResult.Winner(Mark.X), result)
    }

    @Test
    fun `should return Winner when O wins by column`() {
        val board = BoardState(
            cells = listOf(
                listOf(Mark.X, Mark.O, null),
                listOf(Mark.X, Mark.O, null),
                listOf(null, Mark.O, null)
            )
        )
        val result = checkWinnerUseCase.execute(board)
        assertEquals(CheckWinnerResult.Winner(Mark.O), result)
    }

    @Test
    fun `should return Winner when X wins by main diagonal`() {
        val board = BoardState(
            cells = listOf(
                listOf(Mark.X, Mark.O, null),
                listOf(Mark.O, Mark.X, null),
                listOf(null, null, Mark.X)
            )
        )
        val result = checkWinnerUseCase.execute(board)
        assertEquals(CheckWinnerResult.Winner(Mark.X), result)
    }

    @Test
    fun `should return Winner when O wins by anti-diagonal`() {
        val board = BoardState(
            cells = listOf(
                listOf(Mark.X, Mark.X, Mark.O),
                listOf(null, Mark.O, null),
                listOf(Mark.O, null, Mark.X)
            )
        )
        val result = checkWinnerUseCase.execute(board)
        assertEquals(CheckWinnerResult.Winner(Mark.O), result)
    }

    @Test
    fun `should return Draw when board is full with no winner`() {
        val board = BoardState(
            cells = listOf(
                listOf(Mark.X, Mark.O, Mark.X),
                listOf(Mark.X, Mark.O, Mark.O),
                listOf(Mark.O, Mark.X, Mark.X)
            )
        )
        val result = checkWinnerUseCase.execute(board)
        assertEquals(CheckWinnerResult.Draw, result)
    }

    @Test
    fun `should return InProgress when game is not finished`() {
        val board = BoardState(
            cells = listOf(
                listOf(Mark.X, null, null),
                listOf(null, Mark.O, null),
                listOf(null, null, null)
            )
        )
        val result = checkWinnerUseCase.execute(board)
        assertEquals(CheckWinnerResult.InProgress, result)
    }

    @Test
    fun `should return InProgress for empty board`() {
        val board = BoardState.newEmptyBoard()
        val result = checkWinnerUseCase.execute(board)
        assertEquals(CheckWinnerResult.InProgress, result)
    }
}
