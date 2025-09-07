package com.example.dai_android_grupo_4.data.api.repository;

import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.reservas.ReservaDetailResponse;
import com.example.dai_android_grupo_4.model.Reserva;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepositoryImpl implements ApiRepository {

    private final ApiService apiService;

    @Inject
    public ApiRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getAllReservas(ReservaServiceCallback callback) {
        apiService.getReservasList().enqueue(new Callback<List<ReservaDetailResponse>>() {
            @Override
            public void onResponse(Call<List<ReservaDetailResponse>> call, Response<List<ReservaDetailResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<ReservaDetailResponse> results = response.body();
                    List<Reserva> reservas = new ArrayList<>();

                    for (ReservaDetailResponse reserva : results) {
                        reservas.add(new Reserva( reserva.getDisciplina(), reserva.getHorario(), reserva.getProfesor()));
                    }
                    callback.onSuccess(reservas);
                } else {
                    callback.onError(new Exception("Error fetching Reserva list"));
                }
            }

            @Override
            public void onFailure(Call<List<ReservaDetailResponse>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

}
