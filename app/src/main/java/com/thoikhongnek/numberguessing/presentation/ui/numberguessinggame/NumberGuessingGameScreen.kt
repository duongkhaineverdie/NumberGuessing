package com.thoikhongnek.numberguessing.presentation.ui.numberguessinggame

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thoikhongnek.numberguessing.R
import com.thoikhongnek.numberguessing.presentation.ui.component.CustomDialog
import com.thoikhongnek.numberguessing.presentation.ui.numberguessinggame.components.ButtonNumber
import com.thoikhongnek.numberguessing.utils.TAG
import com.thoikhongnek.numberguessing.utils.TypeButton
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun NumberGuessingGameScreen(navController: NavHostController) {
    val numberGuessingGameViewModel: NumberGuessingGameViewModel = koinViewModel()
    val uiState by numberGuessingGameViewModel.stateFlow.collectAsState()

    NumberGuessingGameScreen(
        modifier = Modifier.fillMaxSize(),
        levelGame = uiState.levelGame,
        listNumberRemove = uiState.listNumberRemove,
        listNumberCorrect = uiState.listNumberCorrect,
        onClickButton = numberGuessingGameViewModel::selectButton,
        isFlick = uiState.isFlick,
        isVictory = uiState.isVictory,
        timeClickButton = uiState.timeClickButton,
        onClickNewGame = numberGuessingGameViewModel::newGame,
        timeLeft = uiState.timeLeft,
        onClickNextLevel = numberGuessingGameViewModel::nextGame,
        highScore = uiState.highScore
    )
}

@Composable
fun NumberGuessingGameScreen(
    modifier: Modifier = Modifier,
    levelGame: Int,
    listNumberRemove: List<Int>,
    listNumberCorrect: List<Int>,
    isFlick: Boolean = false,
    onClickButton: (Int) -> Unit,
    onClickNewGame: () -> Unit,
    onClickNextLevel: () -> Unit,
    isVictory: Boolean = false,
    timeClickButton: Int = 0,
    timeLeft: Int = 1,
    highScore: Int = 0,
) {
    val totalNumber = levelGame * levelGame
    var showNewGameAlert by remember { mutableStateOf(false) }
    var showNextLevelAlert by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isVictory, key2 = timeLeft) {
        showNextLevelAlert = isVictory
        if (timeLeft < 0 && !isVictory) {
            showNewGameAlert = true
        } else {
            showNewGameAlert = false
        }
    }
    Column(
        modifier = modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.high_score_regex, highScore), fontWeight = FontWeight.Bold,
                        fontSize = 30.sp, fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = stringResource(id = R.string.level_game_regex, (levelGame - 1)),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(2.dp, Color.Black),
            columns = GridCells.Fixed(sqrt(totalNumber.toDouble()).roundToInt())
        ) {
            items(totalNumber) {
                val number = it + 1
                ButtonNumber(
                    modifier = Modifier.fillMaxSize(),
                    number = number,
                    border = BorderStroke(0.5.dp, Color.Black),
                    typeButton = if (listNumberRemove.contains(number)) {
                        TypeButton.DISABLE
                    } else if (listNumberCorrect.contains(number)) {
                        if (isFlick) {
                            TypeButton.CORRECT
                        } else {
                            TypeButton.ENABLE
                        }
                    } else {
                        TypeButton.ENABLE
                    }
                ) {
                    onClickButton(number)
                }
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            Column {

                Text(
                    text = stringResource(id = R.string.choose_left_regex, (timeLeft + 1)),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    CustomDialog(
        showDialog = timeLeft < 0 && !isVictory,
        onDismissRequest = { /* no-op */ }) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.background(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.img_defeat),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Card(
                modifier = Modifier.padding(bottom = 80.dp),
                onClick = onClickNewGame,
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3280D1),
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 30.dp
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = stringResource(id = R.string.new_game),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }

    CustomDialog(
        showDialog = isVictory,
        onDismissRequest = {/* no-op */ }) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.background(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.img_victory),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Card(
                modifier = Modifier.padding(bottom = 80.dp),
                onClick = onClickNextLevel,
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3280D1),
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 30.dp
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = stringResource(id = R.string.next_level),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
@Preview(name = "NumberGuessingGame", showSystemUi = true)
private fun NumberGuessingGameScreenPreview() {
    NumberGuessingGameScreen(
        modifier = Modifier.fillMaxSize(),
        levelGame = 2,
        listNumberRemove = (1..50).toList(),
        listNumberCorrect = (81..100).toList(),
        onClickButton = {/* no-op */ },
        onClickNewGame = {/* no-op */ },
        onClickNextLevel = {/* no-op */ }
    )
}

