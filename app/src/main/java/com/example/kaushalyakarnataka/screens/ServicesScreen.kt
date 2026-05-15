package com.example.kaushalyakarnataka.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavController) {

    val services = listOf(
        ServiceType("Electrician", Icons.Default.Settings, Color(0xFFFFC107)),
        ServiceType("Plumber", Icons.Default.Build, Color(0xFF2196F3)),
        ServiceType("Carpenter", Icons.Default.Home, Color(0xFF795548))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose a Service", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), MaterialTheme.colorScheme.surface)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Expert Help At Hand",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Verified professionals for your home",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(services) { service ->
                    ServiceCategoryCard(service) {
                        navController.navigate("home?category=${service.name}")
                    }
                }
            }
        }
    }
}

data class ServiceType(val name: String, val icon: ImageVector, val color: Color)

@Composable
fun ServiceCategoryCard(service: ServiceType, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(service.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = null,
                    tint = service.color,
                    modifier = Modifier.size(36.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(24.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "View verified ${service.name}s",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = Color.LightGray.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
