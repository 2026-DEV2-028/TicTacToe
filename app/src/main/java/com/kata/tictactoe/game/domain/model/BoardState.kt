package com.kata.tictactoe.game.domain.model

data class BoardState(
    val cells: List<List<Mark?>>
) {
    companion object {
        const val BOARD_SIZE = 3

        fun newEmptyBoard(): BoardState {
            return BoardState(
                cells = List(BOARD_SIZE) { List(BOARD_SIZE) { null } }
            )
        }
    }

    val isFull: Boolean get() = cells.all { row -> row.all { it != null } }
}