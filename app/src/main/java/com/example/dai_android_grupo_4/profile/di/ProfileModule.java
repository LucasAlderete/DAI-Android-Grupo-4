package com.example.dai_android_grupo_4.profile.di;

import com.example.dai_android_grupo_4.profile.repository.ProfileRepository;
import com.example.dai_android_grupo_4.profile.repository.ProfileRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ProfileModule {

    @Binds
    public abstract ProfileRepository bindProfileRepository(ProfileRepositoryImpl impl);
}
