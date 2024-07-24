package com.johnqualls.reservationapp.client.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ClientScreen() {
    val viewModel: ClientViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Content(state)
}

@Composable
private fun Content(state: ClientUiState) {
    // TODO
}
