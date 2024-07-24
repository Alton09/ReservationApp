package com.johnqualls.reservationapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.johnqualls.reservationapp.client.ui.ClientScreen
import com.johnqualls.reservationapp.provider.ui.ProviderScreen

@Composable
fun ReservationNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "HOME", modifier = modifier) {
        composable("HOME") {
            HomeScreen { navController.navigate("PROVIDER") }
        }
        composable("PROVIDER") {
            ProviderScreen()
        }
        composable("CLIENT") {
            ClientScreen()
        }
    }
}