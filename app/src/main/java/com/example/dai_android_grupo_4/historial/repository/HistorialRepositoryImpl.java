package com.example.dai_android_grupo_4.historial.repository;

import com.example.dai_android_grupo_4.historial.api.HistorialApiService;
import com.example.dai_android_grupo_4.historial.model.AsistenciaPageResponse;
import com.example.dai_android_grupo_4.historial.model.AsistenciaResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class HistorialRepositoryImpl implements HistorialRepository {

    private final HistorialApiService historialApiService;

    @Inject
    public HistorialRepositoryImpl(HistorialApiService historialApiService) {
        this.historialApiService = historialApiService;
    }

    @Override
    public void getHistorialAsistencias(int page, int size, HistorialCallback callback) {
        historialApiService.getHistorialAsistencias(page, size).enqueue(new Callback<AsistenciaPageResponse>() {
            @Override
            public void onResponse(Call<AsistenciaPageResponse> call, Response<AsistenciaPageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AsistenciaResponse> asistencias = response.body().getContent();
                    callback.onSuccess(asistencias);
                } else {
                    callback.onError("Error al cargar historial: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AsistenciaPageResponse> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void getHistorialAsistenciasConFiltros(int page, int size, String fechaInicio, String fechaFin, HistorialCallback callback) {
        historialApiService.getHistorialAsistenciasConFiltros(fechaInicio, fechaFin, page, size).enqueue(new Callback<AsistenciaPageResponse>() {
            @Override
            public void onResponse(Call<AsistenciaPageResponse> call, Response<AsistenciaPageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AsistenciaResponse> asistencias = response.body().getContent();
                    callback.onSuccess(asistencias);
                } else {
                    callback.onError("Error al cargar historial filtrado: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AsistenciaPageResponse> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}