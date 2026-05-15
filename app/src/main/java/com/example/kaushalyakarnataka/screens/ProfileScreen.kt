package com.example.kaushalyakarnataka.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.kaushalyakarnataka.model.ServiceItem
import com.example.kaushalyakarnataka.model.Worker
import com.example.kaushalyakarnataka.viewmodel.WorkerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: WorkerViewModel = viewModel()) {
    val workers by viewModel.workers.collectAsState()
    val context = LocalContext.current
    
    // In a real app, this would be the logged-in user's profile ID
    val currentWorker = workers.firstOrNull() 
    
    var name by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Electrician") }
    var location by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val categories = listOf("Electrician", "Plumber", "Carpenter")
    var expanded by remember { mutableStateOf(false) }

    var showServiceDialog by remember { mutableStateOf(false) }
    var editingService by remember { mutableStateOf<ServiceItem?>(null) }
    var serviceName by remember { mutableStateOf("") }
    var servicePrice by remember { mutableStateOf("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                currentWorker?.id?.let { id ->
                    viewModel.uploadPortfolioImage(id, it)
                }
            }
        }
    )

    LaunchedEffect(currentWorker) {
        currentWorker?.let {
            name = it.name
            title = it.title
            bio = it.bio
            category = it.category
            location = it.location
            phone = it.phone
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Business Profile", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Business Information", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Business Name") }, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium)
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Professional Title") }, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium)
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Service Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Short Bio / Experience") }, modifier = Modifier.fillMaxWidth(), minLines = 3, shape = MaterialTheme.shapes.medium)
            OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location (City/Town)") }, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium)
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium)

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Services & Rates", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                IconButton(onClick = { 
                    editingService = null
                    serviceName = ""
                    servicePrice = ""
                    showServiceDialog = true 
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Service", tint = MaterialTheme.colorScheme.primary)
                }
            }

            currentWorker?.services?.forEach { service ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    ListItem(
                        headlineContent = { Text(service.name, fontWeight = FontWeight.Medium) },
                        trailingContent = { 
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(service.price, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                IconButton(onClick = {
                                    editingService = service
                                    serviceName = service.name
                                    servicePrice = service.price
                                    showServiceDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
                                }
                            }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Portfolio (Verified Work)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Showcase photos of your completed projects to build trust.", style = MaterialTheme.typography.bodySmall)
            
            Button(
                onClick = {
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Upload Project Photo")
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 8.dp)) {
                items(currentWorker?.portfolioImages ?: emptyList()) { imgUrl ->
                    Card(modifier = Modifier.size(120.dp), shape = MaterialTheme.shapes.medium) {
                        AsyncImage(
                            model = imgUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    currentWorker?.let {
                        val updated = it.copy(
                            name = name,
                            title = title,
                            bio = bio,
                            category = category,
                            location = location,
                            phone = phone
                        )
                        viewModel.saveWorker(updated)
                        Toast.makeText(context, "Business Profile Updated!", Toast.LENGTH_SHORT).show()
                    } ?: run {
                        // Create new worker if none exists
                        val newWorker = Worker(
                            name = name,
                            title = title,
                            bio = bio,
                            category = category,
                            location = location,
                            phone = phone
                        )
                        viewModel.saveWorker(newWorker)
                        Toast.makeText(context, "Business Profile Created!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("SAVE ALL CHANGES", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    if (showServiceDialog) {
        AlertDialog(
            onDismissRequest = { showServiceDialog = false },
            title = { Text(if (editingService == null) "Add Service" else "Edit Service") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = serviceName, onValueChange = { serviceName = it }, label = { Text("Service Name (e.g. Fan Repair)") }, shape = MaterialTheme.shapes.medium)
                    OutlinedTextField(value = servicePrice, onValueChange = { servicePrice = it }, label = { Text("Price (e.g. ₹200)") }, shape = MaterialTheme.shapes.medium)
                }
            },
            confirmButton = {
                Button(onClick = {
                    currentWorker?.id?.let { id ->
                        if (editingService == null) {
                            viewModel.addService(id, serviceName, servicePrice)
                        } else {
                            viewModel.updateService(id, editingService!!.copy(name = serviceName, price = servicePrice))
                        }
                        showServiceDialog = false
                    }
                }, shape = MaterialTheme.shapes.medium) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showServiceDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
