package com.thoikhongnek.numberguessing.presentation.ui.countdowntime

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.thoikhongnek.numberguessing.R
import com.thoikhongnek.numberguessing.presentation.ui.component.ButtonTimeCountdown
import com.thoikhongnek.numberguessing.presentation.ui.countdowntime.components.CircleCountdown
import com.thoikhongnek.numberguessing.presentation.ui.home.HomeViewModel
import com.thoikhongnek.numberguessing.presentation.ui.theme.NumberGuessingTheme
import com.thoikhongnek.numberguessing.utils.Constants
import com.thoikhongnek.numberguessing.utils.TAG
import com.thoikhongnek.numberguessing.utils.innerShadow
import com.thoikhongnek.numberguessing.utils.shadow
import org.koin.androidx.compose.koinViewModel

@Composable
fun CountdownTimeScreen(navController: NavHostController) {
    val countdownTimeViewModel: CountdownTimeViewModel = koinViewModel()
    val uiState by countdownTimeViewModel.stateFlow.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.isRingtone) {
        if (uiState.isRingtone) {
            Log.d(TAG, "CountdownTimeScreen: keuekeueueu")
            countdownTimeViewModel.playRingtoneOrVibrate(context)
        }
    }

    CountdownTimeScreen(
        modifier = Modifier.fillMaxSize(),
        onBack = { navController.navigateUp() },
        onForwardNextSecond = countdownTimeViewModel::forwardNextSecondNOw,
        totalSecond = uiState.totalSecond,
        secondNow = uiState.secondNow,
        onActionPauseResume = countdownTimeViewModel::changeStatusRunning,
        isRunning = uiState.isRunning
    )
}

@Composable
fun CountdownTimeScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    totalSecond: Long?,
    secondNow: Long?,
    onForwardNextSecond: (Long) -> Unit,
    onActionPauseResume: () -> Unit,
    isRunning: Boolean = false
) {
    ConstraintLayout(
        modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.Gray.copy(0.5f),
                    )
                )
            )
            .systemBarsPadding()
    ) {
        val (header, body, footer) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(header) {
                    top.linkTo(parent.top)
                }
                .padding(
                    vertical = 10.dp,
                    horizontal = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ButtonTimeCountdown(
                onClick = onBack,
                shape = CircleShape,
                color = Color.White
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(body) {
                    top.linkTo(header.bottom, margin = 40.dp)
                    bottom.linkTo(footer.top)
                    height = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.TopCenter
        ) {
            CircleCountdown(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .aspectRatio(1f),
                totalSecond = totalSecond ?: 0,
                secondRealtime = secondNow ?: 0,
                sizeCircleColor = 40.dp,
                sizeText = 30.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(footer) {
                    bottom.linkTo(parent.bottom, margin = 30.dp)
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
        }
    }
}

@Composable
@Preview(name = "CountdownTime", showSystemUi = true)
private fun CountdownTimeScreenPreview() {
    CountdownTimeScreen(
        modifier = Modifier.fillMaxSize(),
        onBack = { /* no-op */ },
        onForwardNextSecond = { /* no-op */ },
        totalSecond = 500,
        secondNow = 300,
        onActionPauseResume = { /* no-op */ },
        isRunning = true
    )
}

