package com.example.dai_android_grupo_4.di;

import com.example.dai_android_grupo_4.services.clases.ClaseService;
import com.example.dai_android_grupo_4.services.clases.ClaseServiceImpl;
import com.example.dai_android_grupo_4.services.reservas.ReservaService;
import com.example.dai_android_grupo_4.services.reservas.ReservaServiceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ServiceModule {

    @Binds
    @Singleton
    public abstract ReservaService provideReservaService(ReservaServiceImpl implementation);

    @Binds
    @Singleton
    public abstract ClaseService provideClaseService(ClaseServiceImpl implementation);
}
