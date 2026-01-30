package com.kata.tictactoe.game.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import com.kata.tictactoe.core.helper.UiText
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
        uiState = uiState,
        onUserAction = viewModel::onUserAction
    )
}

@Composable
fun GameScreenContent(
    uiState: GameViewModel.UiState,
    onUserAction: (GameViewModel.UserAction) -> Unit = {}
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
            if (uiState.endOfGameMessage != null) {
                Text(
                    text = uiState.endOfGameMessage.asString(),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Button(onClick = { onUserAction(GameViewModel.UserAction.OnReplayButtonClick) }) {
                    Text(
                        text = stringResource(R.string.game_replay_button),
                    )
                }
            } else {
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
            boardState = uiState.boardState,
            onCellClick = { row, col ->
                onUserAction(GameViewModel.UserAction.OnCellClick(row, col))
            }
        )
    }
}

@Preview
@Composable
private fun GameScreenContentPreview() {
    GameScreenContent(
        uiState = GameViewModel.UiState(
            currentPlayer = Player("2026-DEV2-028", Mark.X),
            boardState = BoardState(
                cells = listOf(
                    listOf(Mark.X, null, Mark.O),
                    listOf(null, Mark.X, null),
                    listOf(null, null, Mark.X)
                )
            )
        )
    )
}

@Preview
@Composable
private fun GameScreenContentWinnerPreview() {
    GameScreenContent(
        uiState = GameViewModel.UiState(
            currentPlayer = Player("2026-DEV2-028", Mark.X),
            boardState = BoardState(
                cells = listOf(
                    listOf(Mark.X, Mark.X, Mark.X),
                    listOf(null, Mark.O, null),
                    listOf(null, null, Mark.O)
                )
            ),
            endOfGameMessage = UiText.StringResource(
                R.string.game_win_message,
                args = arrayOf("2026-DEV2-028")
            )
        )
    )
}