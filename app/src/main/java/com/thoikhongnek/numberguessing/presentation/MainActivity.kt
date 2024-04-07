package com.thoikhongnek.numberguessing.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.thoikhongnek.numberguessing.domain.navigation.Destination
import com.thoikhongnek.numberguessing.domain.navigation.NavHostNoteApp
import com.thoikhongnek.numberguessing.domain.navigation.composable
import com.thoikhongnek.numberguessing.presentation.ui.countdowntime.CountdownTimeScreen
import com.thoikhongnek.numberguessing.presentation.ui.home.HomeScreen
import com.thoikhongnek.numberguessing.presentation.ui.numberguessinggame.NumberGuessingGameScreen
import com.thoikhongnek.numberguessing.presentation.ui.theme.NumberGuessingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            NumberGuessingTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavigation(navController = navController)
                }
            }
        }
    }


}

@Composable
private fun SetupNavigation(navController: NavHostController) {
    NavHostNoteApp(navController = navController, startDestination = Destination.HomeScreen) {
        composable(Destination.HomeScreen) { HomeScreen(navController) }
        composable(Destination.NumberGuessingGameScreen) { NumberGuessingGameScreen(navController) }
    }
}