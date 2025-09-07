package com.example.dai_android_grupo_4.data.api.repository.clases;

import com.example.dai_android_grupo_4.data.api.model.clases.ClaseDetailResponse;
import com.example.dai_android_grupo_4.data.api.model.clases.ClaseListResponse;
import com.example.dai_android_grupo_4.model.ClaseDetail;
import com.example.dai_android_grupo_4.model.Disciplina;
import com.example.dai_android_grupo_4.model.PageResponse;
import com.example.dai_android_grupo_4.model.Sede;
import com.example.dai_android_grupo_4.services.clases.api.ClaseApiService;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClaseRepositoryImpl implements ClaseRepository {

    private final ClaseApiService apiService;

    @Inject
    public ClaseRepositoryImpl(ClaseApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getAllClases(int page, int size, Long sedeId, Long disciplinaId, String fecha, ClaseServiceCallBack callback) {
        apiService.getAllClases(page, size, sedeId, disciplinaId, fecha).enqueue(new Callback<PageResponse<ClaseDetailResponse>>() {
            @Override
            public void onResponse(Call<PageResponse<ClaseDetailResponse>> call, Response<PageResponse<ClaseDetailResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ClaseListResponse listResponse = new ClaseListResponse(response.body().getContent());
                    callback.onSuccess(listResponse);
                } else {
                    callback.onError(new Exception("Error fetching clases"));
                }
            }

            @Override
            public void onFailure(Call<PageResponse<ClaseDetailResponse>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getClaseById(Long claseId, ClaseDetailCallback callback) {
        apiService.getClaseById(claseId).enqueue(new Callback<ClaseDetailResponse>() {
            @Override
            public void onResponse(Call<ClaseDetailResponse> call, Response<ClaseDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ClaseDetail claseDetail = ClaseDetail.toClaseDetail(response.body());
                    callback.onSuccess(claseDetail);
                } else {
                    callback.onError(new Exception("Error al obtener el detalle de la clase"));
                }
            }

            @Override
            public void onFailure(Call<ClaseDetailResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
