package com.kata.tictactoe.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kata.tictactoe.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onStartGame: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = null) {
        viewModel.event.collectLatest {
            when (it) {
                is HomeViewModel.Event.OpenGameScreen -> onStartGame(
                    it.firstPlayerName,
                    it.secondPlayerName
                )
            }
        }
    }

    HomeScreenContent(
        uiState = uiState,
        onUserAction = viewModel::onUserAction
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeViewModel.UiState,
    onUserAction: (HomeViewModel.UserAction) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(R.string.home_welcome_title),
                    style = TextStyle(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.home_welcome_sub_title),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = uiState.firstPlayerName,
                onValueChange = {
                    onUserAction(HomeViewModel.UserAction.OnFirstPlayerNameUpdated(it))
                },
                placeholder = {
                    Text(text = stringResource(R.string.home_first_player_name_placeholder))
                },
                isError = uiState.firstPlayerError != null,
                supportingText = {
                    Text(text = uiState.firstPlayerError?.asString() ?: "")
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            TextField(
                value = uiState.secondPlayerName,
                onValueChange = {
                    onUserAction(HomeViewModel.UserAction.OnSecondPlayerNameUpdated(it))
                },
                placeholder = {
                    Text(text = stringResource(R.string.home_second_player_name_placeholder))
                },
                isError = uiState.secondPlayerError != null,
                supportingText = {
                    Text(text = uiState.secondPlayerError?.asString() ?: "")
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Button(
                onClick = { onUserAction(HomeViewModel.UserAction.OnStartButtonClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.home_start_game_button))
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    HomeScreenContent(
        uiState = HomeViewModel.UiState()
    )
}