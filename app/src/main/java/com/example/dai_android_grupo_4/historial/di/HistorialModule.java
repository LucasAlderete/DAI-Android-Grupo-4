package com.example.dai_android_grupo_4.historial.di;

import com.example.dai_android_grupo_4.historial.api.HistorialApiService;
import com.example.dai_android_grupo_4.historial.repository.HistorialRepository;
import com.example.dai_android_grupo_4.historial.repository.HistorialRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public abstract class HistorialModule {

    @Binds
    @Singleton
    abstract HistorialRepository bindHistorialRepository(HistorialRepositoryImpl historialRepositoryImpl);

    @Provides
    @Singleton
    static HistorialApiService provideHistorialApiService(Retrofit retrofit) {
        return retrofit.create(HistorialApiService.class);
    }
}