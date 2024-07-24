package com.johnqualls.reservationapp.core

import com.johnqualls.reservationapp.core.data.ReservationDataSource
import com.johnqualls.reservationapp.core.data.ReservationDataSourceImpl
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