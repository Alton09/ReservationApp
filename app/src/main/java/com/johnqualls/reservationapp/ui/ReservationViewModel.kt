package com.johnqualls.reservationapp.ui

import androidx.lifecycle.ViewModel
import com.johnqualls.reservationapp.data.ReservationDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(private val reservationDataSource: ReservationDataSource) : ViewModel() {

}