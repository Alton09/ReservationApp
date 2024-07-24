package com.johnqualls.reservationapp.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johnqualls.reservationapp.R
import com.johnqualls.reservationapp.core.ui.theme.ReservationAppTheme

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ReservationAppTheme {
        HomeScreen()
    }
}

@Composable
fun HomeScreen(onProviderClick: () -> Unit = {}) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .aspectRatio(145 / 57f),
            painter = painterResource(id = R.drawable.henry_logo_black),
            contentDescription = "Henry Meds Logo"
        )
        Button(modifier = Modifier.width(150.dp), onClick = onProviderClick) {
            Text(text = "Provider")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.width(150.dp), onClick = { /*TODO*/ }) {
            Text(text = "Client")
        }
    }
}