package com.example.dai_android_grupo_4.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.model.ClaseDetail;
import com.example.dai_android_grupo_4.services.clases.ClaseDetailServiceCallback;
import com.example.dai_android_grupo_4.services.clases.ClaseService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ClaseDetailViewModel extends ViewModel {

    private final ClaseService claseService;

    private final MutableLiveData<ClaseDetail> claseDetail = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public ClaseDetailViewModel(ClaseService claseService) {
        this.claseService = claseService;
    }

    public LiveData<ClaseDetail> getClaseDetail() {
        return claseDetail;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchClaseDetails(Long claseId) {
        isLoading.setValue(true);
        claseService.getClaseById(claseId, new ClaseDetailServiceCallback() {
            @Override
            public void onSuccess(ClaseDetail detail) {
                claseDetail.setValue(detail);
                isLoading.setValue(false);
            }

            @Override
            public void onError(String message) {
                error.setValue(message);
                isLoading.setValue(false);
            }
        });
    }
}
