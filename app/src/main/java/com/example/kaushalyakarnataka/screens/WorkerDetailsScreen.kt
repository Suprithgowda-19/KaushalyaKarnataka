package com.example.kaushalyakarnataka.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kaushalyakarnataka.viewmodel.WorkerViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerDetailsScreen(navController: NavController, workerId: String, viewModel: WorkerViewModel = viewModel()) {
    val worker by viewModel.selectedWorker.collectAsState()
    val context = LocalContext.current
    
    var showReviewDialog by remember { mutableStateOf(false) }
    var reviewerName by remember { mutableStateOf("") }
    var reviewComment by remember { mutableStateOf("") }
    var reviewRating by remember { mutableDoubleStateOf(5.0) }

    LaunchedEffect(workerId) {
        viewModel.selectWorker(workerId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expert Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        worker?.let { w ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Large Header Image
                Box {
                    AsyncImage(
                        model = w.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Rating Float
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = String.format(Locale.getDefault(), "%.1f", w.rating),
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Verified Badge
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        shape = CircleShape
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.padding(8.dp).size(24.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = w.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = w.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        
                        FloatingActionButton(
                            onClick = { 
                                Toast.makeText(context, "Contacting ${w.name} at ${w.phone}...", Toast.LENGTH_SHORT).show()
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Call")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "📍 ${w.location}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "About", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = w.bio, style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = "Verified Portfolio", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    if (w.portfolioImages.isEmpty()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("No work photos yet", color = Color.Gray)
                            }
                        }
                    } else {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(w.portfolioImages) { imgUrl ->
                                Card(
                                    modifier = Modifier.size(160.dp),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    AsyncImage(
                                        model = imgUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = "Service Rates", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Card(
                        modifier = Modifier.padding(top = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            w.services.forEach { service ->
                                ListItem(
                                    headlineContent = { Text(service.name, fontWeight = FontWeight.Medium) },
                                    trailingContent = { Text(service.price, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp) },
                                    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Reviews", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        TextButton(onClick = { showReviewDialog = true }) {
                            Text("Post Review")
                        }
                    }
                    
                    w.reviews.forEach { review ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(1.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = review.reviewerName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(text = "⭐ ${review.rating}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = review.comment, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = {
                            Toast.makeText(context, "Hiring request sent! ${w.name} will call you soon.", Toast.LENGTH_LONG).show()
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("HIRE THIS EXPERT", fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { showReviewDialog = false },
            title = { Text("Post a Review") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = reviewerName, onValueChange = { reviewerName = it }, label = { Text("Your Name") }, shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = reviewComment, onValueChange = { reviewComment = it }, label = { Text("Comment") }, minLines = 2, shape = RoundedCornerShape(12.dp))
                    Column {
                        Text("Rating: ${String.format(Locale.getDefault(), "%.1f", reviewRating)}", style = MaterialTheme.typography.labelLarge)
                        Slider(
                            value = reviewRating.toFloat(),
                            onValueChange = { reviewRating = it.toDouble() },
                            valueRange = 1f..5f,
                            steps = 3
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    worker?.id?.let { id ->
                        viewModel.addReview(id, reviewerName, reviewComment, reviewRating)
                        showReviewDialog = false
                        reviewerName = ""
                        reviewComment = ""
                    }
                }, shape = RoundedCornerShape(12.dp)) {
                    Text("Post Review")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReviewDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
