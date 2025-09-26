package com.example.dai_android_grupo_4.data.api.repository;

import com.example.dai_android_grupo_4.data.api.model.AuthResponse;

public interface AuthCallback {
    void onSuccess(AuthResponse response);
    void onError(Throwable t);
}
