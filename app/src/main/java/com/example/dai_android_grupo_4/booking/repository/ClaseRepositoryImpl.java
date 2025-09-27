package com.example.dai_android_grupo_4.booking.repository;

import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClaseRepositoryImpl implements ClaseRepository {

    private final ApiService apiService;

    @Inject
    public ClaseRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getClases(Long sedeId, Long disciplinaId, String fechaInicio, String fechaFin, 
                         Integer page, Integer size, ClaseCallback callback) {
        apiService.getClases(sedeId, disciplinaId, fechaInicio, fechaFin, page, size)
                .enqueue(new Callback<PageClaseDto>() {
            @Override
            public void onResponse(Call<PageClaseDto> call, Response<PageClaseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getContent());
                } else {
                    callback.onError("Error al obtener clases: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PageClaseDto> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void getClasePorId(Long id, ClaseCallback callback) {
        apiService.getClasePorId(id).enqueue(new Callback<ClaseDto>() {
            @Override
            public void onResponse(Call<ClaseDto> call, Response<ClaseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ClaseDto> clases = new ArrayList<>();
                    clases.add(response.body());
                    callback.onSuccess(clases);
                } else {
                    callback.onError("Error al obtener clase: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ClaseDto> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void getSedes(SedeCallback callback) {
        apiService.getSedes().enqueue(new Callback<List<SedeDto>>() {
            @Override
            public void onResponse(Call<List<SedeDto>> call, Response<List<SedeDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener sedes: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SedeDto>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void getInstructores(InstructorCallback callback) {
        apiService.getInstructores().enqueue(new Callback<List<InstructorDto>>() {
            @Override
            public void onResponse(Call<List<InstructorDto>> call, Response<List<InstructorDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener instructores: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<InstructorDto>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void getDisciplinas(DisciplinaCallback callback) {
        apiService.getDisciplinas().enqueue(new Callback<List<DisciplinaDto>>() {
            @Override
            public void onResponse(Call<List<DisciplinaDto>> call, Response<List<DisciplinaDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener disciplinas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<DisciplinaDto>> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}
