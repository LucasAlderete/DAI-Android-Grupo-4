package com.example.dai_android_grupo_4.services;

import com.example.dai_android_grupo_4.data.api.repository.ApiRepository;
import com.example.dai_android_grupo_4.data.api.repository.ReservaServiceCallback;

import javax.inject.Inject;

public class ReservaServiceImpl implements ReservaService {
    private final ApiRepository apiRepository;

    @Inject
    public ReservaServiceImpl(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
    public void getAllReservas(ReservaServiceCallback callback) {
        apiRepository.getAllReservas(callback);
    }

}
