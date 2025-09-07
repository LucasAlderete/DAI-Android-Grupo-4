package com.example.dai_android_grupo_4.data.api.repository.clases;

public interface ClaseRepository {
    void getAllClases(
            int page,
            int size,
            Long sedeId,
            Long disciplinaId,
            String fecha,
            ClaseServiceCallBack callback
    );
}
