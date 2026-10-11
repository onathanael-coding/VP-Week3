package com.oliver.vp_week_3.SoalBonus2

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

val BackgroundDark = Color(0xFF0F172A)
val CardDark = Color(0xFF1E293B)
val BorderDark = Color(0xFF334155)
val AccentCyan = Color(0xFF38BDF8)
val TextMuted = Color(0xFF94A3B8)

@Composable
fun Soal2BView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var spotWisata by remember { mutableStateOf("Fjellheisen Cable Car") }
    var pengalaman by remember { mutableStateOf("Pemandangan langit malam hijau aurora spektakuler dari puncak gunung...") }
    var catatan by remember { mutableStateOf("") }
    var tingkatKepuasan by remember { mutableStateOf("Luar Biasa ★") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = AccentCyan,
        focusedContainerColor = CardDark,
        unfocusedContainerColor = CardDark,
        focusedBorderColor = AccentCyan,
        unfocusedBorderColor = BorderDark,
        focusedLabelColor = AccentCyan,
        unfocusedLabelColor = TextMuted,
        focusedPlaceholderColor = TextMuted.copy(alpha = 0.6f),
        unfocusedPlaceholderColor = TextMuted.copy(alpha = 0.6f)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundDark,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val spotName = spotWisata.ifBlank { "Spot Wisata" }
                    val message = "Draft untuk '$spotName' tersimpan!"

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                },
                containerColor = AccentCyan,
                contentColor = BackgroundDark,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "+",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = BackgroundDark
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF1E40AF), Color(0xFF2C5CB9), Color(0xFF2563EB))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "LOG PERJALANAN",
                        color = Color(0xFF93C5FD),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tromsø, Norway ❄️",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Ekspedisi Aurora Borealis",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }

            OutlinedTextField(
                value = spotWisata,
                onValueChange = { spotWisata = it },
                label = { Text("SPOT WISATA FAVORIT") },
                singleLine = true,
                colors = textFieldColors,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pengalaman,
                onValueChange = { pengalaman = it },
                label = { Text("APA YANG PALING KAMU NIKMATI?") },
                minLines = 3,
                maxLines = 5,
                colors = textFieldColors,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = catatan,
                onValueChange = { catatan = it },
                label = { Text("CATATAN TAMBAHAN / PERLENGKAPAN") },
                placeholder = { Text("(Ketik perlengkapan ekstra di sini...)") },
                colors = textFieldColors,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "TINGKAT KEPUASAN:",
                    color = TextMuted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("Biasa", "Seru", "Luar Biasa ★").forEach { option ->
                        val isSelected = tingkatKepuasan == option
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) AccentCyan else CardDark,
                            modifier = Modifier.clickable { tingkatKepuasan = option }
                        ) {
                            Text(
                                text = option,
                                color = if (isSelected) BackgroundDark else Color.White,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Status: ",
                    color = TextMuted,
                    fontSize = 14.sp
                )
                Text(
                    text = "Draft Tersimpan",
                    color = AccentCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Soal2BPreview() {
    Soal2BView()
}