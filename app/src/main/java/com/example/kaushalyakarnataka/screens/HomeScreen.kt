package com.example.kaushalyakarnataka.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kaushalyakarnataka.components.WorkerCard
import com.example.kaushalyakarnataka.viewmodel.WorkerViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, 
    viewModel: WorkerViewModel = viewModel(),
    initialCategory: String = "All"
) {
    val workers by viewModel.workers.collectAsState()
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var searchQuery by remember { mutableStateOf("") }

    // Restricted to the requested 3 services
    val categories = listOf("All", "Electrician", "Plumber", "Carpenter")

    // Sync selectedCategory with initialCategory when it changes from navigation
    LaunchedEffect(initialCategory) {
        selectedCategory = initialCategory
    }

    val filteredWorkers = workers.filter {
        (selectedCategory == "All" || it.category.equals(selectedCategory, ignoreCase = true)) &&
                (it.name.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kaushalya Karnataka", style = MaterialTheme.typography.titleLarge) },
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
                .padding(16.dp)
        ) {
            // Modern Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Find an expert...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.large,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Category Filter UI
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Workers List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredWorkers) { worker ->
                    WorkerCard(
                        name = worker.name,
                        title = worker.title,
                        rating = String.format(Locale.getDefault(), "%.1f", worker.rating),
                        imageUrl = worker.imageUrl,
                        location = worker.location,
                        phone = worker.phone,
                        chargeLabel = "Starting at",
                        chargeAmount = worker.services.firstOrNull()?.price ?: "N/A",
                        service1 = worker.services.getOrNull(0)?.name ?: "",
                        service1Price = worker.services.getOrNull(0)?.price ?: "",
                        service2 = worker.services.getOrNull(1)?.name ?: "",
                        service2Price = worker.services.getOrNull(1)?.price ?: "",
                        onClick = {
                            navController.navigate("details/${worker.id}")
                        }
                    )
                }
            }
            
            if (filteredWorkers.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No experts found in this category.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }
            }
        }
    }
    
    LaunchedEffect(Unit) {
        viewModel.initDemoData()
    }
}
