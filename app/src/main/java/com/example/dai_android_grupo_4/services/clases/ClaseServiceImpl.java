package com.example.dai_android_grupo_4.services.clases;

import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseRepository;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseServiceCallBack;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClaseServiceImpl implements ClaseService {
    
    private final ClaseRepository claseRepository;
    
    @Inject
    public ClaseServiceImpl(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    @Override
    public void getAllClases(int page, int size, Long sedeId, Long disciplinaId, String fecha, ClaseServiceCallBack callback) {
        claseRepository.getAllClases(page, size, sedeId, disciplinaId, fecha, callback);
    }
}
