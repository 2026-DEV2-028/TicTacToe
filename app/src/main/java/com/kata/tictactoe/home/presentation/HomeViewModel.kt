package com.kata.tictactoe.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kata.tictactoe.R
import com.kata.tictactoe.core.helper.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()


    fun onUserAction(userAction: UserAction) {
        when (userAction) {
            is UserAction.OnFirstPlayerNameUpdated -> _uiState.update {
                it.copy(
                    firstPlayerName = userAction.firstPlayerName,
                    firstPlayerError = null
                )
            }

            is UserAction.OnSecondPlayerNameUpdated -> _uiState.update {
                it.copy(
                    secondPlayerName = userAction.secondPlayerName,
                    secondPlayerError = null
                )
            }

            UserAction.OnStartButtonClick -> {
                if (validatePlayersName()) {
                    viewModelScope.launch {
                        _event.emit(
                            Event.OpenGameScreen(
                                firstPlayerName = uiState.value.firstPlayerName,
                                secondPlayerName = uiState.value.secondPlayerName
                            )
                        )
                    }
                }
            }
        }
    }

    private fun validatePlayersName(): Boolean {
        val firstPlayerName = uiState.value.firstPlayerName
        val secondPlayerName = uiState.value.secondPlayerName

        if (firstPlayerName.isEmpty()) {
            _uiState.update {
                it.copy(
                    firstPlayerError = UiText.StringResource(R.string.error_empty_name)
                )
            }
        }

        if (secondPlayerName.isEmpty()) {
            _uiState.update {
                it.copy(
                    secondPlayerError = UiText.StringResource(R.string.error_empty_name)
                )
            }
        }

        return uiState.value.firstPlayerError == null && uiState.value.secondPlayerError == null
    }

    data class UiState(
        val firstPlayerName: String = "",
        val secondPlayerName: String = "",
        val firstPlayerError: UiText? = null,
        val secondPlayerError: UiText? = null
    )

    sealed class UserAction {
        data class OnFirstPlayerNameUpdated(val firstPlayerName: String) : UserAction()
        data class OnSecondPlayerNameUpdated(val secondPlayerName: String) : UserAction()
        data object OnStartButtonClick : UserAction()
    }

    sealed class Event {
        data class OpenGameScreen(val firstPlayerName: String, val secondPlayerName: String) :
            Event()
    }
}