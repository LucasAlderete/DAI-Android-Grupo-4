package com.example.dai_android_grupo_4.historial.api;

import com.example.dai_android_grupo_4.historial.model.AsistenciaPageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistorialApiService {

    @GET("asistencias/historial")
    Call<AsistenciaPageResponse> getHistorialAsistencias(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("asistencias/historial")
    Call<AsistenciaPageResponse> getHistorialAsistenciasConFiltros(
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin,
            @Query("page") int page,
            @Query("size") int size
    );
}