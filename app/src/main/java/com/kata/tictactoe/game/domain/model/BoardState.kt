package com.kata.tictactoe.game.domain.model

import com.kata.tictactoe.game.domain.GameConstants.BOARD_SIZE
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

data class BoardState(
    val cells: PersistentList<PersistentList<Mark?>>
) {
    companion object {

        fun newEmptyBoard(boardSize: Int = BOARD_SIZE): BoardState {
            return BoardState(
                cells = List(boardSize) { List(boardSize) { null }.toPersistentList() }.toPersistentList()
            )
        }
    }

    val isFull: Boolean get() = cells.all { row -> row.all { it != null } }
}