package com.example.dai_android_grupo_4.data.api.repository.clases;

import com.example.dai_android_grupo_4.data.api.model.clases.ClaseListResponse;

public interface ClaseServiceCallBack {
    void onSuccess(ClaseListResponse claseListResponse);
    void onError(Throwable error);
}
