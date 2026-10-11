package com.oliver.vp_week_3.SoalBonus1

import androidx.compose.foundation.BorderStroke
import com.oliver.vp_week_3.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

val Orange = Color(0xFFD96226)
val Brown = Color(0xFF4A1D0E)
val Crem = Color(0xFFF9F4F1)
val Peach = Color(0xFFFBD9B8)

fun rupiah(angka: Int): String {
    return "Rp " + "%,d".format(angka).replace(',', '.')
}

@Composable
fun Soal1B(modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var jumlah by remember { mutableIntStateOf(1) }
    var besar by remember { mutableStateOf(false) }

    val hargaSatuan = if (besar) 25000 + 6000 else 25000
    val subtotal = hargaSatuan * jumlah
    val pajak = subtotal / 10
    val total = subtotal + pajak

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Crem)
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Kopi Kenangan Senja",
                color = Brown,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text("☕", fontSize = 20.sp)
        }

        Card(
            shape = RoundedCornerShape(14.dp),
            colors = cardColors(Color.White),
            border = BorderStroke(1.dp, Peach)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Color(0xFF6B3319),
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.kopi),
                        contentDescription = null,
                        tint = Color(0xFFFBD9B8),
                        modifier = Modifier.size(64.dp)
                    )

                }
                Spacer(Modifier.height(10.dp))
                Text(
                    "Caramel Latte",
                    color = Brown,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Espresso shot, steamed milk & caramel syrup",
                    color = Brown,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "Rp 25.000 / cup",
                    color = Orange,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            "PILIHAN UKURAN: ",
            color = Brown,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (!besar) Color.White else Color(0xFFFCEBDD))
                    .border(
                        width = if (!besar) {
                            2.dp
                        } else {
                            1.dp
                        },
                        color = if (!besar) {
                            Orange
                        } else {
                            Peach
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        besar = false
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Regular (+0)",
                    color = if (!besar) Orange else Brown,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (besar) Color.White else Color(0xFFFCEBDD))
                    .border(
                        width = if (besar) {
                            2.dp
                        } else {
                            1.dp
                        },
                        color = if (besar) {
                            Orange
                        } else {
                            Peach
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        besar = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Large (+6.000)",
                    color = if (besar) Orange else Brown,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = cardColors(Color.White),
            border = BorderStroke(1.dp, Peach)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Jumlah Pesanan:",
                    color = Brown,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(1.dp, Color(0xFFD0D0D0), RoundedCornerShape(8.dp))
                            .clickable {
                                if (jumlah > 1) {
                                    jumlah--
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("-", color = Brown, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                    Text("$jumlah", color = Brown, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFFCEBDD))
                            .border(1.dp, Color(0xFFD0D0D0), RoundedCornerShape(8.dp))
                            .clickable {
                                jumlah++
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", color = Brown, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFCF3E8))
                .drawBehind {
                    drawRoundRect(
                        color = Orange.copy(alpha = 0.5f),
                        cornerRadius = CornerRadius(12.dp.toPx()),
                        style = Stroke(
                            width = 2f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f))
                        )
                    )
                }
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal:", color = Brown, fontSize = 13.sp)
                Text(rupiah(subtotal), color = Brown, fontSize = 13.sp)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Pajak Resto (10%):", color = Brown, fontSize = 13.sp)
                Text(rupiah(pajak), color = Brown, fontSize = 13.sp)
            }
            HorizontalDivider(color = Peach)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total Tagihan:",
                    color = Brown,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(rupiah(total), color = Orange, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.weight(1f))
        SnackbarHost(snackbarHostState)

        Button(
            onClick = {
                val ukuran = if (besar) "Large" else "Regular"
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "$jumlah Caramel Latte ($ukuran) ditambahkan ke keranjang. Total ${
                            rupiah(
                                total
                            )
                        }"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange)
        ) {
            Text("TAMBAH KE KERANJANG", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1BPreview() {
    Soal1B()
}