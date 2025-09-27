package com.example.dai_android_grupo_4.core.di;

import android.content.Context;

import com.example.dai_android_grupo_4.core.repository.TokenRepository;
import com.example.dai_android_grupo_4.core.repository.TokenRepositoryImpl;
import com.example.dai_android_grupo_4.data.api.AuthApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class TokenModule {
    @Provides
    @Singleton
    public TokenRepository provideTokenRepository(@ApplicationContext Context context, AuthApiService authApiService) {
        return new TokenRepositoryImpl(context, authApiService);
    }
}
