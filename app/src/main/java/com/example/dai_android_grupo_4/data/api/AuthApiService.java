package com.example.dai_android_grupo_4.data.api;

import com.example.dai_android_grupo_4.data.api.model.TokenValidationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AuthApiService {
    @GET("auth/validate")
    Call<TokenValidationResponse> validateToken(@Header("Authorization") String token);
}