package com.example.dai_android_grupo_4.data.api.repository;

import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.AuthResponse;
import com.example.dai_android_grupo_4.data.api.model.LoginRequest;
import com.example.dai_android_grupo_4.data.api.model.RegisterRequest;
import com.example.dai_android_grupo_4.data.api.model.ReservaDetailResponse;
import com.example.dai_android_grupo_4.data.api.model.PageReservaDto;
import com.example.dai_android_grupo_4.data.api.model.ReservaDto;
import com.example.dai_android_grupo_4.data.api.model.VerifyOtpRequest;
import com.example.dai_android_grupo_4.data.api.model.OtpRequest;
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
        apiService.getMisReservas(0, 20).enqueue(new Callback<PageReservaDto>() {
            @Override
            public void onResponse(Call<PageReservaDto> call, Response<PageReservaDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Reserva> reservas = new ArrayList<>();
                    for (ReservaDto reserva : response.body().getContent()) {
                        // Convertir ReservaDto a Reserva (modelo anterior)
                        String disciplina = reserva.getClase() != null && reserva.getClase().getDisciplina() != null 
                            ? reserva.getClase().getDisciplina().getNombre() : "";
                        String horario = reserva.getClase() != null && reserva.getClase().getFechaInicio() != null 
                            ? reserva.getClase().getFechaInicio().toString() : "";
                        String profesor = reserva.getClase() != null && reserva.getClase().getInstructor() != null 
                            ? reserva.getClase().getInstructor().getNombreCompleto() : "";
                        
                        reservas.add(new Reserva(disciplina, horario, profesor));
                    }
                    callback.onSuccess(reservas);
                } else {
                    callback.onError(new Exception("Error fetching Reserva list"));
                }
            }

            @Override
            public void onFailure(Call<PageReservaDto> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void register(RegisterRequest request, AuthCallback callback) {
        apiService.register(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en registro"));
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void login(LoginRequest request, AuthCallback callback) {
        apiService.login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorJson = response.errorBody().string();
                            com.google.gson.Gson gson = new com.google.gson.Gson();
                            AuthResponse errorResponse = gson.fromJson(errorJson, AuthResponse.class);
                            if (errorResponse != null && errorResponse.getMensaje() != null) {
                                callback.onError(new Exception(errorResponse.getMensaje()));
                            } else {
                                callback.onError(new Exception("Error en login"));
                            }
                        } else {
                            callback.onError(new Exception("Error en login"));
                        }
                    } catch (Exception e) {
                        callback.onError(new Exception("Error procesando respuesta de login"));
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void verifyOtp(VerifyOtpRequest request, AuthCallback callback) {
        apiService.verifyOtp(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al verificar código"));
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void resendOtp(OtpRequest request, AuthCallback callback) {
        apiService.resendOtp(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al reenviar código"));
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
