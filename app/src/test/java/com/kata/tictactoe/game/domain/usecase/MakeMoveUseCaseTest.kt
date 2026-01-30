package com.kata.tictactoe.game.domain.usecase

import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MakeMoveUseCaseTest {

    private val makeMoveUseCase = MakeMoveUseCase()

    @Test
    fun `execute on empty cell returns Success with mark placed`() {
        val emptyBoard = BoardState.newEmptyBoard()

        val result = makeMoveUseCase.execute(
            boardState = emptyBoard,
            selectedCol = 1,
            selectedRow = 1,
            playerMark = Mark.X
        )

        assertTrue(result is MoveResult.Success)
        val newBoard = (result as MoveResult.Success).newBoardState
        assertEquals(Mark.X, newBoard.cells[1][1])
    }

    @Test
    fun `execute on already marked cell returns CellAlreadyMarked error`() {
        val boardWithMark = BoardState(
            cells = listOf(
                listOf(null, null, null),
                listOf(null, Mark.X, null),
                listOf(null, null, null)
            )
        )

        val result = makeMoveUseCase.execute(
            boardState = boardWithMark,
            selectedCol = 1,
            selectedRow = 1,
            playerMark = Mark.O
        )

        assertTrue(result is MoveResult.Error.CellAlreadyMarked)
    }

    @Test
    fun `execute on full board returns BoardAlreadyFull error`() {
        val fullBoard = BoardState(
            cells = listOf(
                listOf(Mark.X, Mark.O, Mark.X),
                listOf(Mark.O, Mark.X, Mark.O),
                listOf(Mark.O, Mark.X, Mark.O)
            )
        )

        val result = makeMoveUseCase.execute(
            boardState = fullBoard,
            selectedCol = 0,
            selectedRow = 0,
            playerMark = Mark.X
        )

        assertTrue(result is MoveResult.Error.BoardAlreadyFull)
    }

    @Test
    fun `execute preserves other cells when placing a mark`() {
        val boardWithExistingMarks = BoardState(
            cells = listOf(
                listOf(Mark.X, null, null),
                listOf(null, Mark.O, null),
                listOf(null, null, null)
            )
        )

        val result = makeMoveUseCase.execute(
            boardState = boardWithExistingMarks,
            selectedCol = 2,
            selectedRow = 2,
            playerMark = Mark.X
        )

        assertTrue(result is MoveResult.Success)
        val newBoard = (result as MoveResult.Success).newBoardState
        assertEquals(Mark.X, newBoard.cells[0][0])
        assertEquals(Mark.O, newBoard.cells[1][1])
        assertEquals(Mark.X, newBoard.cells[2][2])
    }
}
