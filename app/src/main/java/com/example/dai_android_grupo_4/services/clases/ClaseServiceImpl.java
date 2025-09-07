package com.example.dai_android_grupo_4.services.clases;

import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseDetailCallback;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseRepository;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseServiceCallBack;
import com.example.dai_android_grupo_4.model.ClaseDetail;

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

    @Override
    public void getClaseById(Long claseId, ClaseDetailServiceCallback callback) {
        claseRepository.getClaseById(claseId, new ClaseDetailCallback() {
            @Override
            public void onSuccess(ClaseDetail claseDetail) {
                callback.onSuccess(claseDetail);
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable.getMessage());
            }
        });
    }
}
