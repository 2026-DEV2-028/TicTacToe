package com.kata.tictactoe.game.domain.usecase

import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark
import javax.inject.Inject

class CheckWinnerUseCase @Inject constructor() {

    /**
     * Checks the given board state to determine if there is a winner, a draw, or if the game is still ongoing.
     *
     * It performs the following checks in order:
     * 1. Rows: Checks if any row is filled with the same mark.
     * 2. Columns: Checks if any column is filled with the same mark.
     * 3. Diagonals: Checks both the main diagonal (top-left to bottom-right) and the anti-diagonal (top-right to bottom-left).
     * 4. Draw: If no winner is found, it checks if the board is full, resulting in a draw.
     * 5. In Progress: If none of the above conditions are met, it means the game is still in progress.
     *
     * @param boardState The current state of the game board.
     * @return A [CheckWinnerResult] which can be one of:
     *         - [CheckWinnerResult.Winner] if a player has won, containing the winning mark.
     *         - [CheckWinnerResult.Draw] if the board is full and there is no winner.
     *         - [CheckWinnerResult.InProgress] if the game is still in progress.
     */
    fun execute(boardState: BoardState): CheckWinnerResult {
        val cells = boardState.cells
        val size = cells.size

        // 1. Check Rows
        for (row in 0 until size) {
            val first = cells[row][0]
            if (first != null && cells[row].all { it == first }) {
                return CheckWinnerResult.Winner(first)
            }
        }

        // 2. Check Columns
        for (col in 0 until size) {
            val first = cells[0][col]
            if (first != null) {
                var allMatch = true
                for (row in 1 until size) {
                    if (cells[row][col] != first) {
                        allMatch = false
                        break
                    }
                }
                if (allMatch) return CheckWinnerResult.Winner(first)
            }
        }

        // 3. Check Main Diagonal (Top-left to Bottom-right)
        val topLeft = cells[0][0]
        if (topLeft != null) {
            var allMatch = true
            for (i in 1 until size) {
                if (cells[i][i] != topLeft) {
                    allMatch = false
                    break
                }
            }
            if (allMatch) return CheckWinnerResult.Winner(topLeft)
        }

        // 4. Check Anti-Diagonal (Top-right to Bottom-left)
        val topRight = cells[0][size - 1]
        if (topRight != null) {
            var allMatch = true
            for (i in 1 until size) {
                if (cells[i][size - 1 - i] != topRight) {
                    allMatch = false
                    break
                }
            }
            if (allMatch) return CheckWinnerResult.Winner(topRight)
        }

        if (boardState.isFull) {
            return CheckWinnerResult.Draw
        }

        return CheckWinnerResult.InProgress
    }
}

sealed class CheckWinnerResult {
    data class Winner(val winnerMark: Mark) : CheckWinnerResult()
    data object Draw : CheckWinnerResult()
    data object InProgress : CheckWinnerResult()
}