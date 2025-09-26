package com.example.dai_android_grupo_4.data.api;

import com.example.dai_android_grupo_4.data.api.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    // RESERVAS
    @GET("reservas")
    Call<List<ReservaDetailResponse>> getReservasList();

    // AUTH
    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<AuthResponse> register(@Body RegisterRequest registerRequest);

    @POST("auth/verificar-codigo")
    Call<AuthResponse> verifyOtp(@Body VerifyOtpRequest verifyOtpRequest);

    @POST("auth/reenviar-codigo")
    Call<AuthResponse> resendOtp(@Body OtpRequest otpRequest);

    // USUARIO
    @GET("usuario/perfil")
    Call<UsuarioResponse> getPerfil();

    @PUT("usuario/perfil")
    Call<UsuarioResponse> updatePerfil(@Body UsuarioRequest usuarioRequest);
}
