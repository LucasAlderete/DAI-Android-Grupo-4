package com.example.dai_android_grupo_4.lessons.api;

import com.example.dai_android_grupo_4.lessons.model.Discipline;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.model.Site;
import com.example.dai_android_grupo_4.lessons.model.response.PageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LessonService {
    @GET("clases")
    Call<PageResponse<Lesson>> getAllLessons(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sedeId") Long sedeId,
            @Query("disciplinaId") Long disciplinaId,
            @Query("fecha") String fecha
    );

    @GET("clases/{id}")
    Call<Lesson> getLessonById(@Path("id") Long claseId);

    @GET("clases/sedes")
    Call<List<Site>> getSites();

    @GET("clases/disciplinas")
    Call<List<Discipline>> getDisciplines();
}
