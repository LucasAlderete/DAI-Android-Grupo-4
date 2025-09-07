package com.example.dai_android_grupo_4.services.clases;

import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseServiceCallBack;

public interface ClaseService {
    void getAllClases(
            int page,
            int size,
            Long sedeId,
            Long disciplinaId,
            String fecha,
            ClaseServiceCallBack callback
    );
}
