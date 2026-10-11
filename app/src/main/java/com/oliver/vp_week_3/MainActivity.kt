package com.oliver.vp_week_3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.oliver.vp_week_3.ui.theme.Soal1View
import com.oliver.vp_week_3.ui.theme.Vpweek3Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vpweek3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Soal1View()
                }
            }
        }
    }
}