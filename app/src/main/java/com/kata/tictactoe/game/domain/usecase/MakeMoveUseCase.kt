package com.kata.tictactoe.game.domain.usecase

import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark
import javax.inject.Inject

class MakeMoveUseCase @Inject constructor() {

    /**
     * Executes a move on the Tic-Tac-Toe board.
     *
     * This function attempts to place a player's mark on a specified cell of the board.
     * It first validates the move:
     * - Checks if the board is already full.
     * - Checks if the selected cell is already occupied.
     *
     * If the move is valid, it returns a new [BoardState] with the mark placed.
     * If the move is invalid, it returns a corresponding [MoveResult.Error].
     *
     * @param boardState The current state of the game board.
     * @param selectedCol The column index (0-based) of the selected cell.
     * @param selectedRow The row index (0-based) of the selected cell.
     * @param playerMark The mark ([Mark.X] or [Mark.O]) to be placed on the board.
     * @return A [MoveResult] which is either [MoveResult.Success] containing the updated board state,
     * or [MoveResult.Error] indicating why the move failed.
     */
    fun execute(
        boardState: BoardState,
        selectedCol: Int,
        selectedRow: Int,
        playerMark: Mark
    ): MoveResult {
        if (boardState.isFull) {
            return MoveResult.Error.BoardAlreadyFull
        }

        if (boardState.cells[selectedRow][selectedCol] != null) {
            return MoveResult.Error.CellAlreadyMarked
        }

        val newBoardState = boardState.copy(
            cells = boardState.cells.mapIndexed { rowIndex, rowList ->
                if (rowIndex == selectedRow) {
                    rowList.mapIndexed { col, cell -> if (col == selectedCol) playerMark else cell }
                } else rowList
            }
        )

        return MoveResult.Success(
            newBoardState = newBoardState
        )
    }
}

sealed class MoveResult {
    data class Success(val newBoardState: BoardState) : MoveResult()
    sealed class Error : MoveResult() {
        data object CellAlreadyMarked : Error()
        data object BoardAlreadyFull : Error()
    }
}