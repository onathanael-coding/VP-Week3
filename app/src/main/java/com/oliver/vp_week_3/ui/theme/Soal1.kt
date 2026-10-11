package com.oliver.vp_week_3.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oliver.vp_week_3.R
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class GameState {
    START,
    WAITING,
    READY,
    TRIAL_COMPLETE,
    FAILED,
    FINISHED
}

@Composable
fun Soal1View() {
    var gameState by remember { mutableStateOf(GameState.START) }
    var currentTrial by remember { mutableIntStateOf(1) }
    var trialResults by remember { mutableStateOf(listOf<Long?>()) }
    var lastTrialTime by remember { mutableStateOf<Long?>(null) }
    var startTimer by remember { mutableLongStateOf(0L) }

    LaunchedEffect(gameState, currentTrial) {
        if (gameState == GameState.WAITING) {
            val randomDelay = Random.nextLong(500, 4500) // random delay 0.5 - 4.5
            delay(randomDelay)

            if (gameState == GameState.WAITING) {
                startTimer = System.currentTimeMillis()
                gameState = GameState.READY
            }
        }
    }

    val validResults = trialResults.filterNotNull()
    val averageTime = if (validResults.isNotEmpty()) validResults.average().toLong() else 0L

    val backgroundColor = when (gameState) {
        GameState.START, GameState.TRIAL_COMPLETE -> Color(0xFF40D2C2) // Toska
        GameState.WAITING -> Color(0xFFB0BEC5)                       // Abu-abu
        GameState.READY -> Color(0xFF4CAF50)                         // Hijau
        GameState.FAILED -> Color(0xFFE53935)                        // Merah
        GameState.FINISHED -> {
            when {
                validResults.isEmpty() -> Color(0xFFE53935) // Merah
                averageTime < 180 -> Color(0xFF4CAF50)    // Hijau
                averageTime < 280 -> Color(0xFF2396EC)    // Biru/Cyan
                averageTime < 450 -> Color(0xFFFF9800)    // Orange
                else -> Color(0xFFE53935)                 // Merah
            }
        }
    }

    val (categoryText, categoryImage) = when {
        validResults.isEmpty() -> Pair("YOU LIKE A SNAIL BRO", R.drawable.jempolbad)
        averageTime < 180 -> Pair("DANG YOU ARE SO FAST BRO!", R.drawable.shap)
        averageTime < 280 -> Pair("YOUR REFLEX IS GOOD", R.drawable.jempolgood)
        averageTime < 450 -> Pair("MEH LIKE OTHER PERSON", R.drawable.soso)
        else -> Pair("YOU LIKE A SNAIL BRO", R.drawable.jempolbad)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable {
                when (gameState) {
                    GameState.START -> {
                        trialResults = emptyList()
                        currentTrial = 1
                        gameState = GameState.WAITING
                    }

                    GameState.WAITING -> {
                        // ketika klik kecepetan langsung ke failed
                        trialResults = trialResults + null
                        lastTrialTime = null
                        if (currentTrial < 3) {
                            gameState = GameState.FAILED
                        } else {
                            gameState = GameState.FINISHED
                        }
                    }
                    GameState.READY -> {
                        val reactionTime = System.currentTimeMillis() - startTimer
                        trialResults = trialResults + reactionTime
                        lastTrialTime = reactionTime
                        if (currentTrial < 3) {
                            gameState = GameState.TRIAL_COMPLETE
                        } else {
                            gameState = GameState.FINISHED
                        }
                    }
                    GameState.TRIAL_COMPLETE -> {
                        currentTrial++
                        gameState = GameState.WAITING
                    }
                    GameState.FAILED -> {
                        currentTrial++
                        gameState = GameState.WAITING
                    }
                    GameState.FINISHED -> {
                        trialResults = emptyList()
                        currentTrial = 1
                        gameState = GameState.START
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            when (gameState) {
                GameState.START -> {
                    Text(
                        text = "Reaction Test",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.flash),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Click to Start",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    if (trialResults.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(32.dp))
                        TrialResultsSummary(trialResults = trialResults)
                    }
                }

                GameState.WAITING -> {
                    Text(
                        text = "Get Ready",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.danger),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Wait for green light..",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "DON'T CLICK YET!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                GameState.READY -> {
                    Text(
                        text = "CLICK NOW!",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.run),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "TAP AS FAST AS YOU CAN!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                GameState.TRIAL_COMPLETE -> {
                    Text(
                        text = "Trial $currentTrial Complete!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.centang),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Time: ${lastTrialTime}ms",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Continue to Trial ${currentTrial + 1}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TrialResultsSummary(trialResults = trialResults)
                }

                GameState.FAILED -> {
                    Text(
                        text = "FAIL!",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.thumbdown),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "You clicked too early, TRY TO READ THE RULE BRO",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = if (currentTrial < 3) "TRY AGAIN" else "View Final Result",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TrialResultsSummary(trialResults = trialResults)
                }

                GameState.FINISHED -> {
                    Text(
                        text = categoryText,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = categoryImage),
                        contentDescription = categoryText,
                        modifier = Modifier
                            .size(160.dp)
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Average: ${if (validResults.isNotEmpty()) "${averageTime}ms" else "N/A"}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Click to Start New Test",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TrialResultsSummary(
                        trialResults = trialResults,
                        averageTime = if (validResults.isNotEmpty()) averageTime else null
                    )
                }
            }
        }
    }
}

@Composable
fun TrialResultsSummary(
    trialResults: List<Long?>,
    averageTime: Long? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Trial Results",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            trialResults.forEach { timeMs ->
                val resultStr = if (timeMs != null) "${timeMs}ms" else "Failed"
                Text(
                    text = resultStr,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
        if (averageTime != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Average Score",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Text(
                text = "${averageTime}ms",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun Soal1Preview() {
    Soal1View()
}