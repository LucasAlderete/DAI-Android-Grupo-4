package com.example.dai_android_grupo_4.preferences.di;

import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.preferences.repository.PreferencesRepository;
import com.example.dai_android_grupo_4.preferences.repository.PreferencesRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class PreferencesModule {

    @Provides
    @Singleton
    public PreferencesRepository providePreferencesRepository(ApiService apiService) {
        return new PreferencesRepositoryImpl(apiService);
    }
}
