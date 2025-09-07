package com.example.dai_android_grupo_4.di;

import com.example.dai_android_grupo_4.data.api.repository.ApiRepository;
import com.example.dai_android_grupo_4.data.api.repository.ApiRepositoryImpl;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseRepository;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseRepositoryImpl;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseRetrofitRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract ApiRepository provideApiRepository(ApiRepositoryImpl implementation);

    @Binds
    @Singleton
    public abstract ClaseRepository provideClaseRepository(ClaseRepositoryImpl implementation);
}
