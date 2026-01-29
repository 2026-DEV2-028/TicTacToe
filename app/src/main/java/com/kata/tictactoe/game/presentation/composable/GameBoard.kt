package com.kata.tictactoe.game.presentation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark

@Composable
fun GameBoard(
    boardState: BoardState,
    onCellClick: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        boardState.cells.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .border(1.dp, Color.Black)
                            .clickable(
                                enabled = cell == null
                            ) {
                                onCellClick(
                                    rowIndex,
                                    colIndex
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        when (cell) {
                            Mark.X -> XMark(modifier = Modifier.padding(24.dp))
                            Mark.O -> OMark(modifier = Modifier.padding(24.dp))
                            null -> {}
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun GameBoardPreview() {
    GameBoard(
        boardState = BoardState(
            cells = listOf(
                listOf(Mark.X, null, Mark.O),
                listOf(Mark.O, Mark.X, null),
                listOf(null, null, Mark.X)
            )
        ),
        onCellClick = { _, _ -> }
    )
}