package com.oliver.vp_week_3.Soal2

import com.oliver.vp_week_3.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun Soal2View(){
    val helper = remember { MutableInteractionSource() }
    val tombolditekan by helper.collectIsPressedAsState()

    var cat = R.drawable.biasa
    if (tombolditekan) {
        cat = R.drawable.mangap
    } else {
        R.drawable.biasa
    }
    var sound = "Purr~"
    if (tombolditekan) {
        sound = "Meow!"
    } else {
        sound = "Purr~"
    }
    var coin by remember { mutableStateOf(0) }
    var value by remember { mutableStateOf(1) }
    var valueBerikutnya = (value * 1.5).roundToInt() // kalikan 1.5 untuk menentukan value berikutnya setelah upgrade
    var cost by remember { mutableStateOf(10) }
    val coinsToUpgrade = maxOf(0, cost - coin)
    val canUpgrade = coin >= cost
    var coinAmount = ""
    if (canUpgrade) {
        coinAmount = "Pay for $cost coins"
    } else {
        coinAmount = "Find $coinsToUpgrade more coins"
    }
    Box(){
        Image(
            painter = painterResource(R.drawable.bg),
            "",
            Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column() {
            Row(Modifier
                .padding(40.dp)
                .padding(top = 30.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Card(
                    modifier = Modifier
                        .width(170.dp)
                        .height(170.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.3f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Your Coins",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )

                        Text(
                            "$coin",
                            Modifier.padding(5.dp),
                            color = Color(0xFF00FF88),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${value} coins per tap",
                            color = Color.White
                            ,fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Column(Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Tap the cat",Modifier.padding(12.dp),
                        fontSize = 25.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                    Image(
                        painter = painterResource(cat),
                        contentDescription = "Tap the Cat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(
                                interactionSource = helper,
                                indication = null,
                                onClick = {coin += value }
                            )
                    )
                    Text(sound,Modifier.padding(12.dp),
                        fontSize = 20.sp,
                        color = Color.White)
                }
            }
            Row(Modifier
                .padding(40.dp)
                .padding(top = 10.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Card(
                    modifier = Modifier
                        .width(400.dp)
                        .height(170.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Give me your coins",
                            Modifier.padding(5.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                        )

                        Text(
                            text = "Next upgrade: +$valueBerikutnya coins per tap",
                            fontSize = 20.sp
                        )

                        Button(
                            onClick = {
                                coin -= cost
                                cost *= 2
                                value = valueBerikutnya
                            },
                            enabled = canUpgrade,
                            modifier = Modifier.width(320.dp)
                                .height(60.dp)
                                .padding(5.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF089C14)
                            )

                        ) {Text(coinAmount, fontSize = 16.sp,
                            fontWeight = FontWeight.Bold) }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2Preview(){
    Soal2View()
}