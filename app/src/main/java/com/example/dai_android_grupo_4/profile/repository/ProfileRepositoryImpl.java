package com.example.dai_android_grupo_4.profile.repository;

import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.UsuarioRequest;
import com.example.dai_android_grupo_4.data.api.model.UsuarioResponse;
import com.example.dai_android_grupo_4.data.api.model.UsuarioUpdateRequest;
import com.example.dai_android_grupo_4.profile.model.Usuario;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ApiService apiService;

    @Inject
    public ProfileRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getPerfil(String token, ProfileCallback callback) {
        apiService.getPerfil("Bearer " + token).enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = mapUsuarioResponse(response.body());
                    callback.onSuccess(usuario);
                } else {
                    callback.onError("Error al cargar perfil");
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void updatePerfil(String token, Usuario usuario, ProfileCallback callback) {
        UsuarioUpdateRequest request = new UsuarioUpdateRequest();
        request.setNombre(usuario.getNombre());
        request.setEmail(usuario.getEmail());
        request.setFotoUrl(usuario.getFotoUrl());

        apiService.updatePerfil("Bearer " + token, request)
                .enqueue(new Callback<UsuarioResponse>() {
                    @Override
                    public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(mapUsuarioResponse(response.body()));
                        } else {
                            callback.onError("Error al actualizar perfil");
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }

    @Override
    public void updateImagenPerfil(String token, MultipartBody.Part imagen, ProfileCallback callback) {
        apiService.updateImagenPerfil("Bearer " + token, imagen)
                .enqueue(new Callback<UsuarioResponse>() {
                    @Override
                    public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(mapUsuarioResponse(response.body()));
                        } else {
                            callback.onError("Error al actualizar la imagen");
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }


    private Usuario mapUsuarioResponse(UsuarioResponse r) {
        return new Usuario(r.getId(), r.getNombre(), r.getEmail(), r.getFotoUrl());
    }
}
