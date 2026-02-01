package com.kata.tictactoe.game.domain.model

import com.kata.tictactoe.game.domain.GameConstants.BOARD_SIZE

data class BoardState(
    val cells: List<List<Mark?>>
) {
    companion object {

        fun newEmptyBoard(boardSize: Int = BOARD_SIZE): BoardState {
            return BoardState(
                cells = List(boardSize) { List(boardSize) { null } }
            )
        }
    }

    val isFull: Boolean get() = cells.all { row -> row.all { it != null } }
}