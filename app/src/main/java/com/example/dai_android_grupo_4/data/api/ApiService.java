package com.example.dai_android_grupo_4.data.api;

import com.example.dai_android_grupo_4.data.api.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    // ================= RESERVAS =================
    @GET("reservas")
    Call<List<ReservaDetailResponse>> getReservasList();

    // ================= AUTH =================
    @POST("auth/login")  // POST /api/auth/login
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")  // POST /api/auth/register
    Call<AuthResponse> register(@Body RegisterRequest registerRequest);

    @POST("auth/verificar-codigo")  // POST /api/auth/verificar-codigo
    Call<AuthResponse> verifyOtp(@Body VerifyOtpRequest verifyOtpRequest);

    @POST("auth/reenviar-codigo")  // POST /api/auth/reenviar-codigo
    Call<AuthResponse> resendOtp(@Body OtpRequest otpRequest);

    // ================= USUARIO =================
    @GET("usuario/perfil")
    Call<UsuarioResponse> getPerfil(@Header("Authorization") String token);

    @PUT("usuario/perfil")
    Call<UsuarioResponse> updatePerfil(@Header("Authorization") String token,
                                       @Body UsuarioRequest usuarioRequest);

}
