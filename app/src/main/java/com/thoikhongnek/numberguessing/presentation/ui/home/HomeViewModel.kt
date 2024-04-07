package com.thoikhongnek.numberguessing.presentation.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onValueChangeMinute(minuteInput: Long) {
        _uiState.update {
            it.copy(
                minuteInput = minuteInput.coerceAtMost(5999)
            )
        }
    }

    fun onValueChangeSecond(secondInput: Long) {
        _uiState.update {
            it.copy(
                secondInput = secondInput.coerceAtMost(359999)
            )
        }
    }
}

data class HomeUiState(
    val minuteInput: Long = 0,
    val secondInput: Long = 0,
)