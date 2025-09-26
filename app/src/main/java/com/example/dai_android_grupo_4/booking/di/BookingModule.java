package com.example.dai_android_grupo_4.booking.di;

import com.example.dai_android_grupo_4.booking.repository.BookingRepository;
import com.example.dai_android_grupo_4.booking.repository.BookingRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class BookingModule {

    @Binds
    public abstract BookingRepository bindBookingRepository(BookingRepositoryImpl impl);
}
