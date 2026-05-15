package com.example.kaushalyakarnataka.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun WorkerCard(
    name: String,
    title: String,
    rating: String,
    imageUrl: String,
    location: String,
    phone: String,
    chargeLabel: String,
    chargeAmount: String,
    service1: String,
    service1Price: String,
    service2: String,
    service2Price: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(modifier = Modifier.height(240.dp)) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Worker Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Premium Badge - Verified
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd),
                    color = Color.White.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(14.dp),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "TRUSTED",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1B5E20),
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Floating Rating Badge
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(14.dp),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            rating,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Shadow Gradient for visibility
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                                startY = 400f
                            )
                        )
                )
            }

            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium
                        )
                        if (phone.isNotBlank()) {
                            Text(
                                text = "📞 $phone",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = chargeAmount,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = chargeLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = location,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.4f)
                )

                Text(
                    "TOP SERVICES",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (service1.isNotEmpty()) {
                    ServiceLine(service1, service1Price)
                }
                if (service2.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ServiceLine(service2, service2Price)
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(18.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        "VIEW PROFILE",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceLine(name: String, price: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Medium
        )
        Text(
            price,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
