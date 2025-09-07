package com.example.dai_android_grupo_4.services.clases.api;

import com.example.dai_android_grupo_4.data.api.model.clases.ClaseDetailResponse;
import com.example.dai_android_grupo_4.model.Clase;
import com.example.dai_android_grupo_4.model.PageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClaseApiService {
    @GET("clases")
    Call<PageResponse<ClaseDetailResponse>> getAllClases(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sedeId") Long sedeId,
            @Query("disciplinaId") Long disciplinaId,
            @Query("fecha") String fecha
    );
}
