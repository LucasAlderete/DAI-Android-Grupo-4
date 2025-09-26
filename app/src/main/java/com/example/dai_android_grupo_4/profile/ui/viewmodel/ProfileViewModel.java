package com.example.dai_android_grupo_4.profile.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.profile.model.Usuario;
import com.example.dai_android_grupo_4.profile.repository.ProfileRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;

@HiltViewModel
public class ProfileViewModel extends ViewModel {

    private final ProfileRepository repository;
    private final MutableLiveData<Usuario> usuarioLive = new MutableLiveData<>();
    private final MutableLiveData<String> errorLive = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public LiveData<Usuario> getUsuarioLive() { return usuarioLive; }
    public LiveData<String> getErrorLive() { return errorLive; }

    public void cargarPerfil(String token) {
        repository.getPerfil(token, new ProfileRepository.ProfileCallback() {
            @Override
            public void onSuccess(Usuario usuario) { usuarioLive.postValue(usuario); }
            @Override
            public void onError(String error) { errorLive.postValue(error); }
        });
    }

    public void actualizarPerfil(String token, Usuario usuario) {
        repository.updatePerfil(token, usuario, new ProfileRepository.ProfileCallback() {
            @Override
            public void onSuccess(Usuario usuario) { usuarioLive.postValue(usuario); }
            @Override
            public void onError(String error) { errorLive.postValue(error); }
        });
    }

}
