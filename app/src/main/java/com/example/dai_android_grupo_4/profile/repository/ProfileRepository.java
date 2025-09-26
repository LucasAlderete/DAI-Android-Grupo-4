package com.example.dai_android_grupo_4.profile.repository;

import com.example.dai_android_grupo_4.profile.model.Usuario;

import okhttp3.MultipartBody;

public interface ProfileRepository {

    interface ProfileCallback {
        void onSuccess(Usuario usuario);
        void onError(String error);
    }

    void getPerfil(String token, ProfileCallback callback);

    void updatePerfil(String token, Usuario usuario, ProfileCallback callback);

}
