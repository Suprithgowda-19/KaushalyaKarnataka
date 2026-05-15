package com.example.kaushalyakarnataka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kaushalyakarnataka.screens.HomeScreen
import com.example.kaushalyakarnataka.ui.theme.KaushalyaKarnatakaTheme
import com.example.kaushalyakarnataka.navigation.AppNavigation
import com.example.kaushalyakarnataka.screens.WorkerDetailsScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            KaushalyaKarnatakaTheme {

                AppNavigation()

            }
        }
    }
}