package com.example.dai_android_grupo_4.data.api;

import retrofit2.Call;
import retrofit2.http.GET;

import com.example.dai_android_grupo_4.data.api.model.reservas.ReservaDetailResponse;
import java.util.List;

public interface ApiService {
    @GET("reservas")
    Call<List<ReservaDetailResponse>> getReservasList();


}
