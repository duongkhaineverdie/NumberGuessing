package com.thoikhongnek.numberguessing.presentation.ui.countdowntime

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class CountdownTimeViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<CountdownTimeState> =
        MutableStateFlow(CountdownTimeState())

    val stateFlow: StateFlow<CountdownTimeState> = _stateFlow.asStateFlow()

    init {
        val secondTotal: String = checkNotNull(savedStateHandle["second"])
        secondTotal.toLongOrNull()?.let { value ->
            _stateFlow.update {
                it.copy(
                    totalSecond = value,
                    secondNow = value
                )
            }
        }
        viewModelScope.launch {
            while (isActive) {
                if (stateFlow.value.isRunning) {
                    val secondNow = stateFlow.value.secondNow
                    secondNow?.let { second ->
                        Log.d("TAG", "Chaynej: $second")
                        if (second >= 1) {
                            val result = second.minus(1)
                            if (result == 0L) {
                                changeStatusRunning()
                                _stateFlow.update {
                                    it.copy(isRingtone = true)
                                }
                            }
                            _stateFlow.update {
                                it.copy(
                                    secondNow = result
                                )
                            }
                        } else {
                            changeStatusRunning()
                        }
                    }
                }
                delay(1000)
            }
        }
    }

    fun forwardNextSecondNOw(secondChange: Long) {
        stateFlow.value.secondNow?.let { secondNow ->
            val secondTemp = secondNow + secondChange
            val totalSecond = stateFlow.value.totalSecond
            totalSecond?.let { total ->
                if (secondTemp in 0..total) {
                    _stateFlow.update {
                        it.copy(
                            secondNow = secondTemp
                        )
                    }
                }
            }
        }
    }

    fun changeStatusRunning() {
        _stateFlow.update {
            it.copy(
                isRunning = !stateFlow.value.isRunning
            )
        }
    }

    fun turnOffStatusRingtone() {
        _stateFlow.update {
            it.copy(
                isRingtone = false
            )
        }
    }

    fun playRingtoneOrVibrate(context: Context, delay: Long = 5000) {
        turnOffStatusRingtone()
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Check ringer mode
        val ringerMode = audioManager.ringerMode

        viewModelScope.launch {
            val ringtone = RingtoneManager.getRingtone(
                context, RingtoneManager.getDefaultUri(
                    RingtoneManager.TYPE_RINGTONE
                )
            )
            if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
                // Play ringtone
                ringtone.play()
            } else {
                // Vibrate (if permission granted)
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.VIBRATE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val vibrator =
                        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (vibrator.hasVibrator()) { // Vibrator availability checking
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                    delay,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            ) // New vibrate method for API Level 26 or higher
                        } else {
                            vibrator.vibrate(delay) // Vibrate method for below API Level 26
                        }
                    }
                }
            }
            delay(delay)
            ringtone.stop()
        }
    }
}

data class CountdownTimeState(
    val totalSecond: Long? = null,
    val secondNow: Long? = null,
    val isRunning: Boolean = false,
    val isRingtone: Boolean = false,
)