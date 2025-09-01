package com.example.dai_android_grupo_4.data.api.repository;

import com.example.dai_android_grupo_4.model.Reserva;

import java.util.List;

public interface ReservaServiceCallback {

    void onSuccess(List<Reserva> reservas);
    void onError(Throwable error);

}
