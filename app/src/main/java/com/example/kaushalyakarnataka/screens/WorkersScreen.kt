package com.example.kaushalyakarnataka.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kaushalyakarnataka.components.WorkerCard
import com.example.kaushalyakarnataka.viewmodel.WorkerViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkersScreen(navController: NavController, viewModel: WorkerViewModel = viewModel()) {
    val workers by viewModel.workers.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredWorkers = workers.filter {
        it.name.contains(searchQuery, ignoreCase = true) || 
        it.category.contains(searchQuery, ignoreCase = true) ||
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("All Experts") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search experts...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredWorkers) { worker ->
                    WorkerCard(
                        name = worker.name,
                        title = worker.title,
                        rating = String.format(Locale.getDefault(), "%.1f", worker.rating),
                        imageUrl = worker.imageUrl,
                        location = worker.location,
                        phone = worker.phone,
                        chargeLabel = "Service starting",
                        chargeAmount = worker.services.firstOrNull()?.price ?: "₹0",
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
        }
    }
}