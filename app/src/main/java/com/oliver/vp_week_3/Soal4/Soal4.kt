package com.oliver.vp_week_3.Soal4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class Move(val emoji: String, val label: String) {
    ROCK("\uD83E\uDEA8", "Rock"),
    PAPER("\uD83D\uDCC4", "Paper"),
    SCISSOR("✂\uFE0F", "Scissor")
}

enum class State {
    INITIAL, PICK, REVEAL, FINISHED
}

enum class Result(val teks: String) {
    WIN("You Win!"),
    LOSE("You Lose!"),
    DRAW("It's A Draw!")
}

@Composable
fun Soal4View(modifier: Modifier = Modifier) {
    val n = 5
    val target = (n / 2) + 1
    var gameState by rememberSaveable { mutableStateOf(State.INITIAL) }
    var skor by rememberSaveable { mutableIntStateOf(0) }
    var skorsistem by rememberSaveable { mutableIntStateOf(0) }
    var best by rememberSaveable { mutableIntStateOf(0) }
    var Player by rememberSaveable { mutableStateOf(Move.ROCK) }
    var AI by rememberSaveable { mutableStateOf(Move.ROCK) }
    var hasil by rememberSaveable { mutableStateOf(Result.DRAW) }
    var urutan by remember { mutableStateOf(Move.entries.shuffled()) }

    LaunchedEffect(gameState) {
        if (gameState == State.REVEAL) {
            delay(700)
            if (skor == target || skorsistem == target) {
                if (skor - skorsistem > best) {
                    best = skor - skorsistem
                }
                gameState = State.FINISHED
            } else {
                urutan = Move.entries.shuffled()
                gameState = State.PICK
            }
        }
    }

    fun pilih(pilihan: Move) {
        Player = pilihan
        AI = Move.entries.random()

        hasil = when {
            Player == AI -> Result.DRAW
            (Player == Move.ROCK && AI == Move.SCISSOR) ||
                    (Player == Move.PAPER && AI == Move.ROCK) ||
                    (Player == Move.SCISSOR && AI == Move.PAPER) -> Result.WIN

            else -> Result.LOSE
        }

        if (hasil == Result.WIN) {
            skor++
        }
        if (hasil == Result.LOSE) {
            skorsistem++
        }
        gameState = State.REVEAL
    }

    fun resetMatch(nextState: State) {
        skor = 0
        skorsistem = 0
        urutan = Move.entries.shuffled()
        gameState = nextState
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            Text("🧑 $skor — $skorsistem \uD83E\uDD16")
            Text("Best of $n")
        }

        when (gameState) {
            State.INITIAL -> {
                Text(
                    "Rock • Paper • Scissors",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 48.dp, bottom = 24.dp)
                )
                Button({ gameState = State.PICK }) { Text("Start") }
            }

            State.PICK -> {
                Text("Pick your move!", modifier = Modifier.padding(top = 48.dp))
                Text("❔ VS ❔", fontSize = 36.sp, modifier = Modifier.padding(vertical = 16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    urutan.forEach { r ->
                        Button({ pilih(r) }) { Text("${r.emoji} ${r.label}") }
                    }
                }
            }

            State.REVEAL -> {
                Text(
                    "${Player.emoji} VS ${AI.emoji}",
                    fontSize = 36.sp,
                    modifier = Modifier.padding(top = 48.dp)
                )
                Text(hasil.teks, modifier = Modifier.padding(top = 8.dp))
            }

            State.FINISHED -> {
                Text(
                    if (skor > skorsistem) "You Win the Match!" else "You Lose the Match!",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 48.dp)
                )
                Text(
                    "Best Score: $best",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button({ resetMatch(State.PICK) },
                        colors = ButtonDefaults
                            .buttonColors(containerColor = Color(0xFF4CAF50)))
                    { Text("Restart") }
                    Button({ resetMatch(State.INITIAL) },
                        colors = ButtonDefaults
                            .buttonColors(containerColor = Color(0xFF4CAF50)))
                    { Text("Exit") }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal4Preview() {
    Soal4View()
}