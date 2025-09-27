package com.example.dai_android_grupo_4.lessons.di;

import com.example.dai_android_grupo_4.lessons.repository.LessonRepository;
import com.example.dai_android_grupo_4.lessons.repository.LessonRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class LessonsModule {
    @Binds
    @Singleton
    public abstract LessonRepository provideLessonRepository(LessonRepositoryImpl implementation);
}
