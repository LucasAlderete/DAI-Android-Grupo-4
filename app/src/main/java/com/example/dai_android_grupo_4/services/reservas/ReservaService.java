package com.example.dai_android_grupo_4.services.reservas;

import com.example.dai_android_grupo_4.data.api.repository.ReservaServiceCallback;

public interface ReservaService {
    void getAllReservas(ReservaServiceCallback callback);
}
