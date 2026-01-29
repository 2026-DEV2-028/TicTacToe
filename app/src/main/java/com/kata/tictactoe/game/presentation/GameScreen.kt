package com.kata.tictactoe.game.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kata.tictactoe.R
import com.kata.tictactoe.game.domain.model.BoardState
import com.kata.tictactoe.game.domain.model.Mark
import com.kata.tictactoe.game.domain.model.Player
import com.kata.tictactoe.game.presentation.composable.GameBoard
import com.kata.tictactoe.game.presentation.composable.OMark
import com.kata.tictactoe.game.presentation.composable.XMark

@Composable
fun GameScreen(
    firstPlayerName: String,
    secondPlayerName: String,
    viewModel: GameViewModel = hiltViewModel(
        creationCallback = { factory: GameViewModel.Factory ->
            factory.create(firstPlayerName = firstPlayerName, secondPlayerName = secondPlayerName)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GameScreenContent(
        uiState = uiState
    )
}

@Composable
fun GameScreenContent(
    uiState: GameViewModel.UiState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            if (uiState.currentPlayer != null) {
                Text(
                    text = stringResource(R.string.game_your_turn, uiState.currentPlayer.name),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Box(modifier = Modifier.size(80.dp)) {
                    when (uiState.currentPlayer.assignedMark) {
                        Mark.X -> XMark()
                        Mark.O -> OMark()
                    }
                }
            }
        }

        GameBoard(
            boardState = BoardState(
                cells = listOf(
                    listOf(Mark.X, null, Mark.O),
                    listOf(null, Mark.X, null),
                    listOf(null, null, Mark.X)
                )
            ),
            onCellClick = { _, _ -> }
        )
    }
}

@Preview
@Composable
private fun GameScreenContentPreview() {
    GameScreenContent(
        uiState = GameViewModel.UiState(
            currentPlayer = Player("2026-DEV2-028", Mark.X)
        )
    )
}