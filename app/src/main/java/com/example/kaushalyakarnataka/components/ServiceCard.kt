package com.example.kaushalyakarnataka.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ServiceCard(serviceName: String) {

    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp),

        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = serviceName,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}