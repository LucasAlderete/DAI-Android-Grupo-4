package com.example.dai_android_grupo_4.historial.repository;

import com.example.dai_android_grupo_4.historial.model.AsistenciaResponse;
import java.util.List;

public interface HistorialRepository {

    interface HistorialCallback {
        void onSuccess(List<AsistenciaResponse> asistencias);
        void onError(String error);
    }

    void getHistorialAsistencias(int page, int size, HistorialCallback callback);

    void getHistorialAsistenciasConFiltros(int page, int size, String fechaInicio, String fechaFin, HistorialCallback callback);
}