package com.example.dai_android_grupo_4.core.repository;

import retrofit2.Call;
import com.example.dai_android_grupo_4.data.api.model.TokenValidationResponse;

public interface TokenRepository {
    void saveToken(String token);
    String getToken();
    void clearToken();
    boolean hasToken();

    Call<TokenValidationResponse> validateToken();
}
