package com.example.dai_android_grupo_4.data.api.repository.clases;

import com.example.dai_android_grupo_4.data.api.model.ClaseApiService;
import com.example.dai_android_grupo_4.data.api.model.clases.ClaseListResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ClaseRetrofitRepository implements ClaseRepository {
    private final ClaseApiService claseApiService;

    @Inject
    public ClaseRetrofitRepository(ClaseApiService claseApiService) {
        this.claseApiService = claseApiService;
    }

    @Override
    public void getAllClases(
            int page,
            int size,
            Long sedeId,
            Long disciplinaId,
            String fecha,
            final ClaseServiceCallBack callback)
    {
        claseApiService.getClases(page, size, sedeId, disciplinaId, fecha).enqueue(new Callback<ClaseListResponse>() {
            @Override
            public void onResponse(Call<ClaseListResponse> call, Response<ClaseListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error fetching classes"));
                }
            }

            @Override
            public void onFailure(Call<ClaseListResponse> call, Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}
