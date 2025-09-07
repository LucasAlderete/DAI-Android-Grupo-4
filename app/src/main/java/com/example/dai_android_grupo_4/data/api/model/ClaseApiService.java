package com.example.dai_android_grupo_4.data.api.model;

import com.example.dai_android_grupo_4.data.api.model.clases.ClaseListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClaseApiService {
    /**
     * Obtiene una lista paginada y filtrada de clases.
     * @param page Número de la página a solicitar.
     * @param size Cantidad de elementos por página.
     * @param sedeId ID de la sede para filtrar (opcional).
     * @param disciplinaId ID de la disciplina para filtrar (opcional).
     * @param fecha Fecha para filtrar (opcional, formato ISO).
     * @return Un objeto Call que contiene la respuesta de la API.
     */
    @GET("clases")
    Call<ClaseListResponse> getClases(
        @Query("page") int page,
        @Query("size") int size,
        @Query("sedeId") Long sedeId,
        @Query("disciplinaId") Long disciplinaId,
        @Query("fecha") String fecha
    );
}
