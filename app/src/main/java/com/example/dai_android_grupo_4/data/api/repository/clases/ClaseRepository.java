package com.example.dai_android_grupo_4.data.api.repository.clases;

import com.example.dai_android_grupo_4.model.Clase;

import java.util.List;

public interface ClaseRepository {
    void getAllClases(
            int page,
            int size,
            Long sedeId,
            Long disciplinaId,
            String fecha,
            ClaseServiceCallBack callback
    );

    void getClaseById(Long claseId, ClaseDetailCallback callback);
}
