package com.example.dai_android_grupo_4.data.api;

import com.example.dai_android_grupo_4.data.api.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // ================= RESERVAS =================
    @GET("reservas")
    Call<PageReservaDto> getMisReservas(@Query("page") Integer page, @Query("size") Integer size);

    @POST("reservas")
    Call<ReservaDto> crearReserva(@Body CrearReservaDto crearReservaDto);

    @DELETE("reservas/{id}")
    Call<ReservaDto> cancelarReserva(@Path("id") Long id);

    @GET("reservas/proximas")
    Call<List<ReservaDto>> getProximasReservas();

    // ================= CATÁLOGO DE CLASES =================
    @GET("clases")
    Call<PageClaseDto> getClases(@Query("sedeId") Long sedeId, 
                                @Query("disciplinaId") Long disciplinaId,
                                @Query("fechaInicio") String fechaInicio,
                                @Query("fechaFin") String fechaFin,
                                @Query("page") Integer page,
                                @Query("size") Integer size);

    @GET("clases/{id}")
    Call<ClaseDto> getClasePorId(@Path("id") Long id);

    @GET("clases/sedes")
    Call<List<SedeDto>> getSedes();

    @GET("clases/instructores")
    Call<List<InstructorDto>> getInstructores();

    @GET("clases/disciplinas")
    Call<List<DisciplinaDto>> getDisciplinas();

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
                                       @Body UsuarioUpdateRequest usuarioRequest);

}
