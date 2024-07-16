package com.johnqualls.reservationapp

import com.johnqualls.reservationapp.data.ReservationDataSource
import com.johnqualls.reservationapp.data.ReservationDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideReservationDataSource(): ReservationDataSource {
        return ReservationDataSourceImpl()
    }

}