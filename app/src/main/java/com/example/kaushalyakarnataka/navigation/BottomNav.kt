package com.example.kaushalyakarnataka.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Services", Icons.Default.Build, "services"),
        BottomNavItem("Workers", Icons.Default.Person, "workers"),
        BottomNavItem("Profile", Icons.Default.AccountCircle, "profile")
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    NavigationBar {
        items.forEach { item ->
            // Check if the current destination matches the route or its parent in the hierarchy
            val isSelected = currentDestination?.hierarchy?.any { 
                it.route?.startsWith(item.route) == true 
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo("home") {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                }
            )
        }
    }
}
