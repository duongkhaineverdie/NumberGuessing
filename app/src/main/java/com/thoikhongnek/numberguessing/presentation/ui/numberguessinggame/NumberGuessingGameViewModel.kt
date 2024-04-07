package com.thoikhongnek.numberguessing.presentation.ui.numberguessinggame

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoikhongnek.numberguessing.domain.interactors.GetHighScoreUseCase
import com.thoikhongnek.numberguessing.domain.interactors.SaveHighScoreUseCase
import com.thoikhongnek.numberguessing.utils.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

class NumberGuessingGameViewModel(
    savedStateHandle: SavedStateHandle,
    val getHighScoreUseCase: GetHighScoreUseCase,
    val saveHighScoreUseCase: SaveHighScoreUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<NumberGuessingGameState> =
        MutableStateFlow(NumberGuessingGameState())

    val stateFlow: StateFlow<NumberGuessingGameState> = _stateFlow.asStateFlow()

    init {
        getHighScore()
        viewModelScope.launch {
            val timeDelay = 300L
            while (isActive) {
                if (stateFlow.value.timeFlickMillis > 0L) {
                    val result = stateFlow.value.timeFlickMillis - timeDelay
                    _stateFlow.update {
                        it.copy(
                            isFlick = if (result <= 0) false else !stateFlow.value.isFlick,
                            timeFlickMillis = result,
                        )
                    }
                }
                delay(timeDelay)
            }
        }
    }

    fun selectButton(number: Int) {
        val levelGame = stateFlow.value.levelGame
        val totalButtonNumber = levelGame * levelGame
        if (number > stateFlow.value.correctNumber) {
            val listRemove =
                stateFlow.value.listNumberRemove + (number..totalButtonNumber).toList()
                    .distinct()
            _stateFlow.update {
                it.copy(
                    listNumberRemove = listRemove,
                    listNumberCorrect = (1..<number).toList() - listRemove.toSet(),
                    isFlick = true,
                    timeFlickMillis = 1000,
                    timeClickButton = stateFlow.value.timeClickButton + 1,
                    timeLeft = stateFlow.value.timeLeft - 1
                )
            }
        } else if (number < stateFlow.value.correctNumber) {
            val listRemove = (stateFlow.value.listNumberRemove + (1..number).toList()).distinct()
            _stateFlow.update {
                it.copy(
                    listNumberRemove = listRemove,
                    listNumberCorrect = (number..totalButtonNumber).toList() - listRemove.toSet(),
                    isFlick = true,
                    timeFlickMillis = 1000,
                    timeClickButton = stateFlow.value.timeClickButton + 1,
                    timeLeft = stateFlow.value.timeLeft - 1
                )
            }
        } else {
            _stateFlow.update {
                it.copy(
                    isVictory = true,
                    timeClickButton = stateFlow.value.timeClickButton + 1
                )
            }
        }
    }

    fun newGame() {
        val highScore = stateFlow.value.levelGame - 1
        if (highScore > stateFlow.value.highScore) {
            saveHighScore(highScore)
        }
        val numberButton = 2 * 2
        _stateFlow.update {
            it.copy(
                correctNumber = Random.nextInt(1, numberButton),
                isVictory = false,
                isFlick = false,
                listNumberCorrect = (1..numberButton).toList(),
                listNumberRemove = arrayListOf(),
                timeClickButton = 0,
                timeLeft = sqrt(2F).toInt(),
                levelGame = 2
            )
        }
    }

    fun nextGame() {
        val highScore = stateFlow.value.levelGame - 1
        if (highScore > stateFlow.value.highScore) {
            saveHighScore(highScore)
        }
        val levelGame = stateFlow.value.levelGame + 1
        val totalButton = levelGame * levelGame
        _stateFlow.update {
            it.copy(
                correctNumber = Random.nextInt(1, totalButton),
                isVictory = false,
                isFlick = false,
                listNumberCorrect = (1..totalButton).toList(),
                listNumberRemove = arrayListOf(),
                timeClickButton = 0,
                timeLeft = sqrt(levelGame.toFloat()).toInt(),
                levelGame = levelGame
            )
        }
    }

    private fun getHighScore() {
        viewModelScope.launch {
            getHighScoreUseCase(Unit).collectLatest { result ->
                result.onSuccess { value ->
                    _stateFlow.update {
                        it.copy(
                            highScore = value
                        )
                    }
                }
            }
        }
    }

    private fun saveHighScore(score: Int) {
        viewModelScope.launch {
            saveHighScoreUseCase(score).onSuccess {
                getHighScore()
            }
        }
    }
}

data class NumberGuessingGameState(
    val levelGame: Int = 2,
    val correctNumber: Int = 1,
    val listNumberRemove: List<Int> = arrayListOf(),
    val listNumberCorrect: List<Int> = (1..levelGame * levelGame).toList(),
    val isFlick: Boolean = false,
    val timeFlickMillis: Long = 0,
    val isVictory: Boolean = false,
    val timeClickButton: Int = 0,
    val timeLeft: Int = sqrt(levelGame.toFloat()).toInt(),
    val highScore: Int = 0,
)