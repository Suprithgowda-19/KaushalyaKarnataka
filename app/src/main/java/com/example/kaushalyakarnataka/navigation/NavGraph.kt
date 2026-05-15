package com.example.kaushalyakarnataka.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kaushalyakarnataka.screens.HomeScreen
import com.example.kaushalyakarnataka.screens.ProfileScreen
import com.example.kaushalyakarnataka.screens.ServicesScreen
import com.example.kaushalyakarnataka.screens.WorkerDetailsScreen
import com.example.kaushalyakarnataka.screens.WorkersScreen
import com.example.kaushalyakarnataka.viewmodel.WorkerViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Create the ViewModel here to share it among all screens
    val workerViewModel: WorkerViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = "home?category={category}",
                arguments = listOf(navArgument("category") { 
                    type = NavType.StringType
                    defaultValue = "All"
                })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "All"
                HomeScreen(navController, viewModel = workerViewModel, initialCategory = category)
            }
            
            composable("services") {
                ServicesScreen(navController)
            }
            
            composable("workers") {
                WorkersScreen(navController, viewModel = workerViewModel)
            }
            
            composable("profile") {
                ProfileScreen(viewModel = workerViewModel)
            }

            composable(
                route = "details/{workerId}",
                arguments = listOf(navArgument("workerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val workerId = backStackEntry.arguments?.getString("workerId") ?: ""
                WorkerDetailsScreen(navController, workerId, viewModel = workerViewModel)
            }
        }
    }
}
