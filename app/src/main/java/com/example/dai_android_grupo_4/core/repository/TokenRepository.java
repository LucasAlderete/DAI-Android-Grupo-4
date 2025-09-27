package com.example.dai_android_grupo_4.core.repository;

import com.example.dai_android_grupo_4.data.api.model.TokenValidationResponse;
import retrofit2.Call;

public interface TokenRepository {
    void saveToken(String token);
    String getToken();
    void clearToken();
    boolean hasToken();
    Call<TokenValidationResponse> validateToken();
}
